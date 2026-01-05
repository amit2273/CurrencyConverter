package com.revidd.did.presentation.di

import com.revidd.did.presentation.viewmodel.SignInViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        SignInViewModel(signInUseCase = get(), signInQRCodeUseCase = get())
    }

}