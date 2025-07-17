package com.example.data

import com.example.data.api.ApiErrorResponse
import com.example.data.api.ExchangeRatesApi
import com.example.data.db.CurrencyLocalDataSource
import com.example.data.db.RateLocalDataSource
import com.example.data.preference.PrefsLastUpdated
import com.example.data.repository.CurrencyRepositoryImpl
import com.example.domain.model.CurrencyRate
import com.google.gson.Gson
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRepositoryImplTest {

    private val api: ExchangeRatesApi = mockk()
    private val rateLocalDataSource: RateLocalDataSource = mockk()
    private val currencyLocalDataSource: CurrencyLocalDataSource = mockk()
    private val prefs: PrefsLastUpdated = mockk(relaxed = true)

    private lateinit var repository: CurrencyRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = CurrencyRepositoryImpl(
            api = api,
            rateLocalDataSource = rateLocalDataSource,
            currencyLocalDataSource = currencyLocalDataSource,
            prefs = prefs,
            appId = "test_app_id"
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getExchangeRates uses cached data if within 30 minutes`() = runTest {
        val cachedRates = listOf(CurrencyRate("USD", 1.0), CurrencyRate("EUR", 0.85))
        coEvery { prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES) } returns System.currentTimeMillis()
        coEvery { rateLocalDataSource.getAll() } returns cachedRates

        val result = repository.getExchangeRates()

        assertEquals(cachedRates, result)
    }

    @Test
    fun `getExchangeRates fetches from API and saves when cache is expired`() = runTest {
        val now = System.currentTimeMillis()
        val apiRates = mapOf("USD" to 1.0, "EUR" to 0.85)

        val response = mockk<Response<com.example.data.model.ExchangeRatesResponse>>()
        coEvery { prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES) } returns (now - 31 * 60 * 1000)
        coEvery { api.getLatestRates(any()) } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns com.example.data.model.ExchangeRatesResponse( apiRates, 12345677)
        coEvery { rateLocalDataSource.saveAll(apiRates) } just Runs
        coEvery { prefs.set(PrefsLastUpdated.KEY_EXCHANGE_RATES, any()) } just Runs

        val result = repository.getExchangeRates()

        assertEquals(apiRates.map { CurrencyRate(it.key, it.value) }, result)
    }

    @Test
    fun `getExchangeRates throws error if API fails`() = runTest {
        val now = System.currentTimeMillis()
        val errorJson = """{"message":"fail","description":"invalid app id"}"""
        val responseBody = ResponseBody.create("application/json".toMediaTypeOrNull(), errorJson)

        val response = mockk<Response<com.example.data.model.ExchangeRatesResponse>>()
        coEvery { prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES) } returns (now - 31 * 60 * 1000)
        coEvery { api.getLatestRates(any()) } returns response
        every { response.isSuccessful } returns false
        every { response.errorBody() } returns responseBody

        val exception = kotlin.runCatching {
            repository.getExchangeRates()
        }.exceptionOrNull()

        assertNotNull(exception)
        assertTrue(exception!!.message!!.contains("fail") && exception.message!!.contains("invalid app id"))
    }

    @Test
    fun `getAvailableCurrencies returns cached data if cache is valid`() = runTest {
        val cached = mapOf("USD" to "US Dollar", "EUR" to "Euro")
        coEvery { currencyLocalDataSource.isCacheValid() } returns true
        coEvery { currencyLocalDataSource.getAll() } returns cached

        val result = repository.getAvailableCurrencies()

        assertEquals(cached, result)
    }

    @Test
    fun `getAvailableCurrencies fetches from API and saves if cache is invalid`() = runTest {
        val newData = mapOf("INR" to "Rupee", "GBP" to "Pound")
        val response = mockk<Response<Map<String, String>>>()
        coEvery { currencyLocalDataSource.isCacheValid() } returns false
        coEvery { api.getCurrencies() } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns newData
        coEvery { currencyLocalDataSource.saveAll(newData) } just Runs

        val result = repository.getAvailableCurrencies()

        assertEquals(newData, result)
    }

    @Test
    fun `getAvailableCurrencies returns fallback on API failure`() = runTest {
        val fallback = mapOf("JPY" to "Yen")
        val errorJson = """{"message":"fail","description":"not authorized"}"""
        val responseBody = ResponseBody.create("application/json".toMediaTypeOrNull(), errorJson)

        val response = mockk<Response<Map<String, String>>>()
        coEvery { currencyLocalDataSource.isCacheValid() } returns false
        coEvery { api.getCurrencies() } returns response
        every { response.isSuccessful } returns false
        every { response.errorBody() } returns responseBody
        coEvery { currencyLocalDataSource.getAll() } returns fallback

        val result = repository.getAvailableCurrencies()

        assertEquals(fallback, result)
    }

    @Test
    fun `getAvailableCurrencies throws error if API fails and cache is empty`() = runTest {
        val errorJson = """{"message":"fail","description":"invalid app id"}"""
        val responseBody = ResponseBody.create("application/json".toMediaTypeOrNull(), errorJson)

        // Mock error response
        val response: Response<Map<String, String>> = mockk()
        every { response.isSuccessful } returns false
        every { response.errorBody() } returns responseBody

        // Setup mocks
        coEvery { currencyLocalDataSource.isCacheValid() } returns false
        coEvery { currencyLocalDataSource.getAll() } returns emptyMap()
        coEvery { api.getCurrencies() } returns response

        // Run and assert
        val exception = runCatching {
            repository.getAvailableCurrencies()
        }.exceptionOrNull()

        assertNotNull("Expected an exception but got null", exception)
        assertTrue(exception!!.message!!.contains("invalid app id"))
    }

    @Test
    fun `getLastUpdatedTime returns prefs time`() = runTest {
        coEvery { prefs.get(PrefsLastUpdated.KEY_EXCHANGE_RATES) } returns 12345678L

        val result = repository.getLastUpdatedTime()

        assertEquals(12345678L, result)
    }
}
