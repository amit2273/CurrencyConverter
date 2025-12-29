package com.revidd.did.presentation.state

sealed interface SignInIntent{
    data class SignIn(val email : String, val password : String) : SignInIntent
}