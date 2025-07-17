package com.example.data.repository

import com.example.data.db.RateDao
import com.example.data.db.RateEntity
import com.example.data.db.RateLocalDataSource
import com.example.domain.model.CurrencyRate

internal class RateLocalDataSourceImpl(
    private val rateDao: RateDao,
) : RateLocalDataSource {

    override suspend fun getAll(): List<CurrencyRate> {
        return rateDao.getAll().map {
            CurrencyRate(it.currencyCode, it.rateAgainstUSD)
        }
    }

    override suspend fun saveAll(rates: Map<String, Double>) {
        rateDao.clearAll()
        rateDao.insertAll(rates.map { RateEntity(it.key, it.value) })
    }
}
