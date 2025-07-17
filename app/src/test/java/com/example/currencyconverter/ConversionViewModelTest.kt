package com.example.currencyconverter

import com.example.currencyconverter.presentation.viewmodel.ConversionIntent
import com.example.currencyconverter.presentation.viewmodel.ConversionViewModel
import com.example.domain.model.ConversionResult
import com.example.domain.model.CurrencyRate
import com.example.domain.usecase.GetAvailableCurrenciesUseCase
import com.example.domain.usecase.GetConvertedRatesUseCase
import io.mockk.coEvery
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ConversionViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var convertUseCase: GetConvertedRatesUseCase
    private lateinit var getCurrenciesUseCase: GetAvailableCurrenciesUseCase
    private lateinit var viewModel: ConversionViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        convertUseCase = mockk()
        getCurrenciesUseCase = mockk()

        viewModel = ConversionViewModel(convertUseCase, getCurrenciesUseCase)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCurrencies success updates availableCurrencies`() = runTest {
        val fakeCurrencies = mapOf("USD" to "US Dollar", "EUR" to "Euro")

        coEvery { getCurrenciesUseCase() } returns fakeCurrencies

        viewModel = ConversionViewModel(convertUseCase, getCurrenciesUseCase) // Reinit to trigger init block
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(fakeCurrencies, state.availableCurrencies)
        assertEquals(null, state.error)
    }

    @Test
    fun `handleIntent Convert success updates conversions`() = runTest {
        val fakeRates = listOf(
            ConversionResult("EUR", 0.85),
            ConversionResult("JPY", 110.0)
        )
        val fakeCurrencies = mapOf("USD" to "US Dollar", "EUR" to "Euro")
        coEvery { getCurrenciesUseCase() } returns fakeCurrencies

        viewModel = ConversionViewModel(convertUseCase, getCurrenciesUseCase)

        coEvery { convertUseCase("USD", 1.0) } returns fakeRates

        viewModel.handleIntent(ConversionIntent.Convert("USD", 1.0))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(fakeRates, state.conversions)
        assertEquals(false, state.isLoading)
        assertEquals(null, state.error)
    }

    @Test
    fun `handleIntent Convert failure updates error state`() = runTest {
        coEvery { convertUseCase("USD", 1.0) } throws RuntimeException("Something went wrong")

        viewModel.handleIntent(ConversionIntent.Convert("USD", 1.0))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("Something went wrong", state.error)
        assertEquals(false, state.isLoading)
    }
}
