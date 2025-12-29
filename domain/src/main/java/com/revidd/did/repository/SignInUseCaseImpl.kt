package com.revidd.did.repository

import com.revidd.did.model.SignInData
import com.revidd.did.usecase.SignInUseCase

class SignInUseCaseImpl(private val repository: SignInRepository) : SignInUseCase {

    override suspend fun execute(inputT: SignInData): Result<Boolean> {
        return repository.signIn(inputT)
    }
}