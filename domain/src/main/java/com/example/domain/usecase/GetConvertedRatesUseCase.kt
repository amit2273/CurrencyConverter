package com.example.domain.usecase

import com.example.domain.model.ConversionResult

interface GetConvertedRatesUseCase {
    suspend operator fun invoke(baseCurrency: String, amount: Double): List<ConversionResult>
}
