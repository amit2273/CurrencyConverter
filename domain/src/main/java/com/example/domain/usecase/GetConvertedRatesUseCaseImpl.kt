package com.example.domain.usecase

import com.example.domain.model.ConversionResult
import com.example.domain.repository.CurrencyRepository
private const val CURRENCY_USD = "USD"

internal class GetConvertedRatesUseCaseImpl(
    private val repository: CurrencyRepository
) : GetConvertedRatesUseCase {

    override suspend fun invoke(baseCurrency: String, amount: Double): List<ConversionResult> {
        val rates = repository.getExchangeRates()
        val baseRate = if (baseCurrency == CURRENCY_USD) 1.0
        else rates.find { it.currencyCode == baseCurrency }?.rateAgainstUSD ?: return emptyList()

        return rates.map {
            val rate = it.rateAgainstUSD / baseRate
            ConversionResult(
                currencyCode = it.currencyCode,
                convertedAmount = amount * rate
            )
        }
    }
}
