package com.revidd.did.di

import com.revidd.did.usecase.VideoDetailsUseCase
import com.revidd.did.usecase.VideoDetailsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<VideoDetailsUseCase> { VideoDetailsUseCaseImpl(get()) }
}
