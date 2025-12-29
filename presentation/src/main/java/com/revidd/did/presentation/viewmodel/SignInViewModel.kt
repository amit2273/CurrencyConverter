package com.revidd.did.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revidd.did.model.SignInData
import com.revidd.did.presentation.state.SignInIntent
import com.revidd.did.presentation.state.SignInUiState
import com.revidd.did.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun handleIntent(intent : SignInIntent){
        when(intent){
            SignInIntent.SignIn -> {
                viewModelScope.launch {
                    signInUseCase.execute(SignInData(
                        email = "vijay@revidd.com",
                        password = "123456"
                    )).onSuccess {
                        println("Testtttttt Success")

                    }.onFailure {
                        println("Testtttttt Failure")
                    }
                }
            }
        }

    }


}