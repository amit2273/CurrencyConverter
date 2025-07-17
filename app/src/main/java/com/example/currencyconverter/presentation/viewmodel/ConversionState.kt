package com.example.currencyconverter.presentation.viewmodel

import com.example.domain.model.ConversionResult

data class ConversionState(
    val isLoading: Boolean = false,
    val conversions: List<ConversionResult> = emptyList(),
    val error: String? = null,
    val availableCurrencies: Map<String, String> = emptyMap()
)