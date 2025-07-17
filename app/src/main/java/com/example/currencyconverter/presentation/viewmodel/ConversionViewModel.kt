package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetConvertedRatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConversionViewModel(
    private val useCase: GetConvertedRatesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ConversionState())
    val state: StateFlow<ConversionState> = _state

    fun handleIntent(intent: ConversionIntent) {
        when (intent) {
            is ConversionIntent.Convert -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        val result = useCase(intent.baseCurrency, intent.amount)
                        _state.value = ConversionState(conversions = result)
                    } catch (e: Exception) {
                        _state.value = ConversionState(error = e.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}
