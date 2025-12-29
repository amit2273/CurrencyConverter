package com.revidd.did.data.api

import com.revidd.did.data.dto.SignInRequest
import com.revidd.did.data.dto.SignInResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface SignInApiService {

    @GET("store/v1/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponseDto>
}
