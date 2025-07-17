package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAvailableCurrenciesUseCase
import com.example.domain.usecase.GetConvertedRatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConversionViewModel(
    private val convertUseCase: GetConvertedRatesUseCase,
    private val getCurrenciesUseCase: GetAvailableCurrenciesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ConversionState())
    val state: StateFlow<ConversionState> = _state

    init {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            try {
                val currencies = getCurrenciesUseCase()
                _state.value = _state.value.copy(availableCurrencies = currencies)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Failed to load currencies")
            }
        }
    }

    fun handleIntent(intent: ConversionIntent) {
        when (intent) {
            is ConversionIntent.Convert -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        val result = convertUseCase(intent.baseCurrency, intent.amount)
                        _state.value = _state.value.copy(conversions = result, isLoading = false)
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(error = e.message ?: "Unknown error", isLoading = false)
                    }
                }
            }
        }
    }
}
