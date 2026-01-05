package com.revidd.did.presentation.state

data class SignInUiState(val isLoading : Boolean = false,
                         val isSuccess : Boolean = false,
                         val qrCode : Any = "")
