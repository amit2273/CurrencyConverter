package com.example.data.repository

import com.example.data.api.ApiErrorResponse
import com.example.data.api.ExchangeRatesApi
import com.example.data.db.RateDao
import com.example.data.db.RateEntity
import com.example.domain.model.CurrencyRate
import com.example.domain.repository.CurrencyRepository
import com.google.gson.Gson

class CurrencyRepositoryImpl(
    private val api: ExchangeRatesApi,
    private val dao: RateDao,
    private val appId: String,
    private val getTime: () -> Long,
    private val getLastUpdated: suspend () -> Long,
    private val setLastUpdated: suspend (Long) -> Unit
) : CurrencyRepository {

    override suspend fun getExchangeRates(): List<CurrencyRate> {
        val now = getTime()
        val lastUpdated = getLastUpdated()

        return if ((now - lastUpdated) > 1800) {
            val response = api.getLatestRates(appId)

            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Empty response")
                dao.clearAll()
                dao.insertAll(body.rates.map { RateEntity(it.key, it.value) })
                setLastUpdated(body.timestamp)
                body.rates.map { CurrencyRate(it.key, it.value) }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ApiErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                val message = errorResponse?.description ?: "Unknown error"
                throw Exception("API Error: $message")
            }
        } else {
            dao.getAll().map { CurrencyRate(it.currencyCode, it.rateAgainstUSD) }
        }
    }

    override suspend fun getLastUpdatedTime(): Long = getLastUpdated()
}
