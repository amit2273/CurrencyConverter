package com.example.data.repository

import com.example.data.api.ApiErrorResponse
import com.example.data.api.ExchangeRatesApi
import com.example.data.db.CurrencyDao
import com.example.data.db.CurrencyEntity
import com.example.data.db.RateDao
import com.example.data.db.RateEntity
import com.example.data.preference.PrefsLastUpdated
import com.example.domain.model.CurrencyRate
import com.example.domain.repository.CurrencyRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyRepositoryImpl(
    private val api: ExchangeRatesApi,
    private val dao: RateDao,
    private val appId: String,
    private val currencyDao: CurrencyDao,
    private val prefs: PrefsLastUpdated
) : CurrencyRepository {

    override suspend fun getExchangeRates(): List<CurrencyRate> = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val lastUpdated = prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES)

        if ((now - lastUpdated) > 30 * 60 * 1000) {
            val response = api.getLatestRates(appId)
            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Empty response")
                dao.clearAll()
                dao.insertAll(body.rates.map { RateEntity(it.key, it.value) })
                prefs.set(PrefsLastUpdated.KEY_EXCHANGE_RATES, now)
                return@withContext body.rates.map { CurrencyRate(it.key, it.value) }
            } else {
                val errorMessage = parseError(response.errorBody()?.string())
                throw Exception("API Error: $errorMessage")
            }
        } else {
            return@withContext dao.getAll().map { CurrencyRate(it.currencyCode, it.rateAgainstUSD) }
        }
    }

    override suspend fun getAvailableCurrencies(): Map<String, String> {
        val now = System.currentTimeMillis()
        val lastFetched = prefs.get(PrefsLastUpdated.KEY_CURRENCY_LIST)
        val cache = currencyDao.getAllCurrencies()

        return if (cache.isNotEmpty() && (now - lastFetched < 30 * 60 * 1000)) {
            cache.associate { it.currencyCode to it.currencyName }
        } else {
            val response = api.getCurrencies()

            if (response.isSuccessful) {
                val data = response.body() ?: emptyMap()
                currencyDao.clear()
                currencyDao.insertAll(
                    data.map { CurrencyEntity(it.key, it.value) }
                )
                prefs.set(PrefsLastUpdated.KEY_CURRENCY_LIST, now)
                data
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = try {
                    val error = Gson().fromJson(errorJson, ApiErrorResponse::class.java)
                    "${error.message}: ${error.description}"
                } catch (e: Exception) {
                    "Unexpected error: ${response.code()}"
                }

                if (cache.isNotEmpty()) {
                    cache.associate { it.currencyCode to it.currencyName }
                } else {
                    throw Exception(errorMessage)
                }
            }
        }
    }


    override suspend fun getLastUpdatedTime(): Long {
        return prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES)
    }

    private fun parseError(errorBody: String?): String {
        return try {
            val error = Gson().fromJson(errorBody, ApiErrorResponse::class.java)
            "${error.message}: ${error.description}"
        } catch (e: Exception) {
            "Unexpected error"
        }
    }
}
