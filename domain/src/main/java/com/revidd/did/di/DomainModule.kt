package com.revidd.did.di

import com.revidd.did.repository.SignInUseCaseImpl
import com.revidd.did.usecase.SignInUseCase
import com.revidd.did.usecase.VideoDetailsUseCase
import com.revidd.did.usecase.VideoDetailsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<VideoDetailsUseCase> { VideoDetailsUseCaseImpl(get()) }

    factory<SignInUseCase> { SignInUseCaseImpl(get()) }
}
