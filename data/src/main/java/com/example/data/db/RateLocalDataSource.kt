package com.example.data.db

import com.example.domain.model.CurrencyRate

interface RateLocalDataSource {
    suspend fun getAll(): List<CurrencyRate>
    suspend fun saveAll(rates: Map<String, Double>)
}
