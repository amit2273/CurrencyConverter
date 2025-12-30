package com.revidd.did.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revidd.did.model.SignInData
import com.revidd.did.presentation.state.SignInEffect
import com.revidd.did.presentation.state.SignInIntent
import com.revidd.did.presentation.state.SignInUiState
import com.revidd.did.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    private val _signInEffect = MutableSharedFlow<SignInEffect>()
    val signInEffect = _signInEffect.asSharedFlow()



    fun handleIntent(intent : SignInIntent){
        when(intent){
            is SignInIntent.SignIn -> {
                viewModelScope.launch {
                    signInUseCase.execute(SignInData(
                        email = intent.email,
                        password = intent.password
                    )).onSuccess {
                        _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                        _signInEffect.emit(SignInEffect.NavigateToHome)

                    }.onFailure {
                        _signInEffect.emit(SignInEffect.ShowToast)
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }

    }


}