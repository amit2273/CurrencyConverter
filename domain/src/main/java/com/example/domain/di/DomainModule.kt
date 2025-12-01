package com.example.domain.di

import com.example.domain.usecase.GetAvailableCurrenciesUseCase
import com.example.domain.usecase.GetAvailableCurrenciesUseCaseImpl
import com.example.domain.usecase.GetConvertedRatesUseCase
import com.example.domain.usecase.GetConvertedRatesUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<GetConvertedRatesUseCase> { GetConvertedRatesUseCaseImpl(get()) }
    factory<GetAvailableCurrenciesUseCase> { GetAvailableCurrenciesUseCaseImpl(get()) }
}
