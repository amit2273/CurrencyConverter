package com.example.data.db

interface CurrencyLocalDataSource {
    suspend fun getAll(): Map<String, String>
    suspend fun saveAll(currencies: Map<String, String>)
    suspend fun isCacheValid(): Boolean
}
