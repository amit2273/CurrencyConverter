package viewmodel

sealed class ConversionIntent {
    data class Convert(val baseCurrency: String, val amount: Double) : ConversionIntent()
    data class ManipulateAmount(val enteredString : String) : ConversionIntent()




}
