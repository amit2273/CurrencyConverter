package di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.ConversionViewModel

val currencyConverterModule = module {
    viewModel {
        ConversionViewModel(
            convertUseCase = get(),
            getCurrenciesUseCase = get()
        )
    }
}