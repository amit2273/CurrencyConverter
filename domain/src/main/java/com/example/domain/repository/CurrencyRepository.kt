package com.example.domain.repository

import com.example.domain.model.CurrencyRate

interface CurrencyRepository {
    suspend fun getExchangeRates(): List<CurrencyRate>
    suspend fun getAvailableCurrencies(): Map<String, String>
    suspend fun getLastUpdatedTime(): Long
}