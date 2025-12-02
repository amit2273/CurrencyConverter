package com.example.data.repository

import com.example.data.api.ApiErrorResponse
import com.example.data.api.ExchangeRatesApi
import com.example.data.db.CurrencyLocalDataSource
import com.example.data.db.RateLocalDataSource
import com.example.data.preference.PrefsLastUpdated
import com.example.domain.model.CurrencyRate
import com.example.domain.repository.CurrencyRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CurrencyRepositoryImpl(
    private val api: ExchangeRatesApi,
    private val rateLocalDataSource: RateLocalDataSource,
    private val currencyLocalDataSource: CurrencyLocalDataSource,
    private val prefs: PrefsLastUpdated,
    private val appId: String
) : CurrencyRepository {

    override suspend fun getExchangeRates(): List<CurrencyRate> = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val lastUpdated = prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES)

        if ((now - lastUpdated) > 30 * 60 * 1000) {
            val response = api.getLatestRates(appId)
            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Empty response")
                rateLocalDataSource.saveAll(body.rates)
                prefs.set(PrefsLastUpdated.KEY_EXCHANGE_RATES, now)
                body.rates.map { CurrencyRate(it.key, it.value) }
            } else {
                throw Exception("API Error: ${parseError(response.errorBody()?.string())}")
            }
        } else {
            rateLocalDataSource.getAll()
        }
    }

    override suspend fun getAvailableCurrencies(): Map<String, String> {
        return if (currencyLocalDataSource.isCacheValid()) {
            currencyLocalDataSource.getAll()
        } else {
            val response = api.getCurrencies()
            if (response.isSuccessful) {
                val data = response.body() ?: emptyMap()
                currencyLocalDataSource.saveAll(data)
                data
            } else {
                val error = parseError(response.errorBody()?.string())
                val fallback = currencyLocalDataSource.getAll()
                if (fallback.isNotEmpty()) fallback
                else throw Exception("API Error: $error")
            }
        }
    }

    override suspend fun getLastUpdatedTime(): Long {
        return prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES)
    }

    suspend fun testFun(): Flow<String>{
        delay(200)
        return flow {
            emit("Hallo")
            emit("buffalo")
        }
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

