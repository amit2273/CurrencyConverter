package com.example.domain.usecase

interface GetAvailableCurrenciesUseCase {
    suspend operator fun invoke(): Map<String, String>
}
