package com.revidd.did.di

import com.revidd.did.usecase.SignInQRCodeUseCase
import com.revidd.did.usecase.SignInQRCodeUseCaseImpl
import com.revidd.did.usecase.SignInUseCaseImpl
import com.revidd.did.usecase.SignInUseCase
import com.revidd.did.usecase.VideoDetailsUseCase
import com.revidd.did.usecase.VideoDetailsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    factory<VideoDetailsUseCase> { VideoDetailsUseCaseImpl(get()) }

    factory<SignInUseCase> { SignInUseCaseImpl(get()) }

    factory<SignInQRCodeUseCase> { SignInQRCodeUseCaseImpl(get(), get()) }
}
