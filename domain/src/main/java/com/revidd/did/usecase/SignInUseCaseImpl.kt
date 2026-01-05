package com.revidd.did.usecase

import com.revidd.did.model.SignInData
import com.revidd.did.repository.SignInRepository

class SignInUseCaseImpl(private val repository: SignInRepository) : SignInUseCase {

    override suspend fun execute(inputT: SignInData): Result<Boolean> {
        return repository.signIn(inputT)
    }
}