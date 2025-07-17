package com.example.currencyconverter.presentation.viewmodel

sealed class ConversionIntent {
    data class Convert(val baseCurrency: String, val amount: Double) : ConversionIntent()
}
