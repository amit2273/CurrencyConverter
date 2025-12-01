package com.example.data

import com.example.data.db.CurrencyDao
import com.example.data.db.CurrencyEntity
import com.example.data.preference.PrefsLastUpdated
import com.example.data.db.CurrencyLocalDataSource
import com.example.data.repository.CurrencyLocalDataSourceImpl
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyLocalDataSourceImplTest {

    private lateinit var currencyDao: CurrencyDao
    private lateinit var prefs: PrefsLastUpdated
    private lateinit var dataSource: CurrencyLocalDataSource

    @Before
    fun setUp() {
        currencyDao = mockk(relaxed = true)
        prefs = mockk(relaxed = true)
        dataSource = CurrencyLocalDataSourceImpl(currencyDao, prefs)
    }

    @Test
    fun `getAll returns mapped currencies`() = runTest {
        val entities = listOf(
            CurrencyEntity("USD", "US Dollar"),
            CurrencyEntity("EUR", "Euro")
        )
        coEvery { currencyDao.getAllCurrencies() } returns entities
        val result = dataSource.getAll()
        assertEquals(mapOf("USD" to "US Dollar", "EUR" to "Euro"), result)
    }

    @Test
    fun `saveAll clears and inserts currencies and updates prefs`() = runTest {
        val currencies = mapOf("INR" to "Indian Rupee", "GBP" to "British Pound")

        coEvery { currencyDao.clear() } just Runs
        coEvery { currencyDao.insertAll(any()) } just Runs
        coEvery { prefs.set(PrefsLastUpdated.KEY_CURRENCY_LIST, any()) } just Runs

        dataSource.saveAll(currencies)

        coVerify { currencyDao.clear() }
        coVerify {
            currencyDao.insertAll(
                currencies.map { CurrencyEntity(it.key, it.value) }
            )
        }
        coVerify { prefs.set(PrefsLastUpdated.KEY_CURRENCY_LIST, any()) }
    }

    @Test
    fun `isCacheValid returns true if cached within 30 minutes`() = runTest {
        val now = System.currentTimeMillis()
        coEvery { prefs.get(PrefsLastUpdated.KEY_CURRENCY_LIST) } returns now - (10 * 60 * 1000)

        val result = dataSource.isCacheValid()

        assertEquals(true, result)
    }

    @Test
    fun `isCacheValid returns false if cached more than 30 minutes ago`() = runTest {
        val now = System.currentTimeMillis()
        coEvery { prefs.get(PrefsLastUpdated.KEY_CURRENCY_LIST) } returns now - (40 * 60 * 1000)

        val result = dataSource.isCacheValid()

        assertEquals(false, result)
    }
}
