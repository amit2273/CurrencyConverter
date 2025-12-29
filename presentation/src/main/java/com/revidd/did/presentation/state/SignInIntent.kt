package com.revidd.did.presentation.state

sealed interface SignInIntent{
    data object SignIn : SignInIntent
}