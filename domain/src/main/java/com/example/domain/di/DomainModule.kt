package com.example.domain.di

import com.example.domain.usecase.GetConvertedRatesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetConvertedRatesUseCase(get()) }
}
