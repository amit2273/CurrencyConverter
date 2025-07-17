package com.example.domain.usecase

import com.example.domain.repository.CurrencyRepository

class GetAvailableCurrenciesUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): Map<String, String> {
        return repository.getAvailableCurrencies()
    }
}
