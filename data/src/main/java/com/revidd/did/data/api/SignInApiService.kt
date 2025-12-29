package com.revidd.did.data.api

import com.revidd.did.data.dto.SignInRequestDto
import com.revidd.did.data.dto.SignInResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInApiService {

    @POST("store/v1/signin")
    suspend fun signIn(@Body signInRequestDto: SignInRequestDto): Response<SignInResponseDto>
}
