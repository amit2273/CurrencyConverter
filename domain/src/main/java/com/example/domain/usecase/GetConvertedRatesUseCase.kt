package com.example.domain.usecase

import com.example.domain.model.ConversionResult
import com.example.domain.repository.CurrencyRepository

class GetConvertedRatesUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(baseCurrency: String, amount: Double): List<ConversionResult> {
        val rates = repository.getExchangeRates()
        val baseRate = if (baseCurrency == "USD") 1.0
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