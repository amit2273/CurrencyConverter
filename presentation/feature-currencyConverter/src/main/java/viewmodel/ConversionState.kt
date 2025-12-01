package viewmodel

import ONE
import com.example.domain.model.ConversionResult

data class ConversionState(
    val isLoading: Boolean = false,
    val conversions: List<ConversionResult> = emptyList(),
    val error: String? = null,
    val availableCurrencies: Map<String, String> = emptyMap(),
    val enteredAmount : String = ONE
)