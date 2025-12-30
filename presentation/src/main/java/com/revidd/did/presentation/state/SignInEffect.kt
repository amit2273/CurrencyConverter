package com.revidd.did.presentation.state

sealed interface SignInEffect {
    data object NavigateToHome : SignInEffect
    data object ShowToast : SignInEffect
}