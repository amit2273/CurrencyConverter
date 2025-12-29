package com.revidd.did.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revidd.did.model.SignInData
import com.revidd.did.presentation.state.SignInIntent
import com.revidd.did.presentation.state.SignInUiState
import com.revidd.did.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun handleIntent(intent : SignInIntent){
        when(intent){
            is SignInIntent.SignIn -> {
                viewModelScope.launch {
                    signInUseCase.execute(SignInData(
                        email = intent.email,
                        password = intent.password
                    )).onSuccess {
                        _uiState.update { it.copy(isLoading = false, isSuccess = true) }

                    }.onFailure {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }

    }


}