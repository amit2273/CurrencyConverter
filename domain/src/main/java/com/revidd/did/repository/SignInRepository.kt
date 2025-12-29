package com.revidd.did.repository

import com.revidd.did.model.SignInData

interface SignInRepository {

    suspend fun signIn(data : SignInData) : Result<Boolean>
}