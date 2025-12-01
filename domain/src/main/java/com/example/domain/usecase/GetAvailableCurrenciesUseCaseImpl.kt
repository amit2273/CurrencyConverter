package com.example.domain.usecase

import com.example.domain.repository.CurrencyRepository

internal class GetAvailableCurrenciesUseCaseImpl(
    private val repository: CurrencyRepository
) : GetAvailableCurrenciesUseCase {
    override suspend fun invoke(): Map<String, String> {
        return repository.getAvailableCurrencies()
    }
}
