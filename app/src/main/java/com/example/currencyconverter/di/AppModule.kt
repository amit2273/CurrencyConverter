package com.example.currencyconverter.di

import com.example.currencyconverter.presentation.viewmodel.ConversionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ConversionViewModel(get()) }
}
