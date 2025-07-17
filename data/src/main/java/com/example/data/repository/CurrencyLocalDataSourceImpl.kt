package com.example.data.repository

import com.example.data.db.CurrencyDao
import com.example.data.db.CurrencyEntity
import com.example.data.db.CurrencyLocalDataSource
import com.example.data.preference.PrefsLastUpdated

internal class CurrencyLocalDataSourceImpl(
    private val currencyDao: CurrencyDao,
    private val prefs: PrefsLastUpdated
) : CurrencyLocalDataSource {

    override suspend fun getAll(): Map<String, String> {
        return currencyDao.getAllCurrencies().associate { it.currencyCode to it.currencyName }
    }

    override suspend fun saveAll(currencies: Map<String, String>) {
        currencyDao.clear()
        currencyDao.insertAll(currencies.map { CurrencyEntity(it.key, it.value) })
        prefs.set(PrefsLastUpdated.KEY_CURRENCY_LIST, System.currentTimeMillis())
    }

    override suspend fun isCacheValid(): Boolean {
        val now = System.currentTimeMillis()
        val lastFetched = prefs.get(PrefsLastUpdated.KEY_CURRENCY_LIST)
        return now - lastFetched < 30 * 60 * 1000
    }
}
