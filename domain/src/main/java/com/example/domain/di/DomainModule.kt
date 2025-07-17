package com.example.domain.di

import com.example.domain.usecase.GetAvailableCurrenciesUseCase
import com.example.domain.usecase.GetConvertedRatesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetConvertedRatesUseCase(get()) }
    factory { GetAvailableCurrenciesUseCase(get()) }
}
