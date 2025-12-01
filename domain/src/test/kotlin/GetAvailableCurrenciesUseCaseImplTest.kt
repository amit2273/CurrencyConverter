import com.example.domain.repository.CurrencyRepository
import com.example.domain.usecase.GetAvailableCurrenciesUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAvailableCurrenciesUseCaseImplTest {

    private lateinit var repository: CurrencyRepository
    private lateinit var useCase: GetAvailableCurrenciesUseCaseImpl

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetAvailableCurrenciesUseCaseImpl(repository)
    }

    @Test
    fun `invoke returns currencies from repository`() = runTest {
        val fakeCurrencies = mapOf("USD" to "US Dollar", "EUR" to "Euro")
        coEvery { repository.getAvailableCurrencies() } returns fakeCurrencies

        val result = useCase()

        assertEquals(fakeCurrencies, result)
    }
}
