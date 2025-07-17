import com.example.domain.model.ConversionResult
import com.example.domain.model.CurrencyRate
import com.example.domain.repository.CurrencyRepository
import com.example.domain.usecase.GetConvertedRatesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetConvertedRatesUseCaseTest {

    private lateinit var repository: CurrencyRepository
    private lateinit var useCase: GetConvertedRatesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetConvertedRatesUseCase(repository)
    }

    @Test
    fun `returns correct conversion when base currency is USD`() = runTest {
        // Given
        val rates = listOf(
            CurrencyRate("USD", 1.0),
            CurrencyRate("EUR", 0.85),
            CurrencyRate("JPY", 110.0)
        )
        coEvery { repository.getExchangeRates() } returns rates

        // When
        val result = useCase("USD", 2.0)

        // Then
        val expected = listOf(
            ConversionResult("USD", 2.0),
            ConversionResult("EUR", 1.7),
            ConversionResult("JPY", 220.0)
        )
        assertEquals(expected, result)
    }

    @Test
    fun `returns correct conversion when base currency is not USD`() = runTest {
        val rates = listOf(
            CurrencyRate("USD", 1.0),
            CurrencyRate("EUR", 0.85),
            CurrencyRate("JPY", 110.0)
        )
        coEvery { repository.getExchangeRates() } returns rates

        val result = useCase("EUR", 2.0)

        val eurToUsd = 1.0 / 0.85
        val expected = listOf(
            ConversionResult("USD", 2.0 * (1.0 / 0.85)),
            ConversionResult("EUR", 2.0),
            ConversionResult("JPY", 2.0 * (110.0 / 0.85))
        )
        assertEquals(expected.size, result.size)
        expected.zip(result).forEach { (exp, actual) ->
            assertEquals(exp.currencyCode, actual.currencyCode)
            assertEquals(exp.convertedAmount, actual.convertedAmount, 0.0001)
        }
    }

    @Test
    fun `returns empty list if base currency is not found`() = runTest {
        val rates = listOf(
            CurrencyRate("USD", 1.0),
            CurrencyRate("EUR", 0.85)
        )
        coEvery { repository.getExchangeRates() } returns rates

        val result = useCase("INR", 10.0)

        assertEquals(emptyList<ConversionResult>(), result)
    }
}
