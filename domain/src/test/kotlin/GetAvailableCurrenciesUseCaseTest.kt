import com.example.domain.repository.CurrencyRepository
import com.example.domain.usecase.GetAvailableCurrenciesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAvailableCurrenciesUseCaseTest {

    private lateinit var repository: CurrencyRepository
    private lateinit var useCase: GetAvailableCurrenciesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetAvailableCurrenciesUseCase(repository)
    }

    @Test
    fun `invoke returns currencies from repository`() = runTest {
        // Given
        val fakeCurrencies = mapOf("USD" to "US Dollar", "EUR" to "Euro")
        coEvery { repository.getAvailableCurrencies() } returns fakeCurrencies

        // When
        val result = useCase()

        // Then
        assertEquals(fakeCurrencies, result)
    }
}
