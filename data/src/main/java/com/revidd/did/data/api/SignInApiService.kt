package com.revidd.did.data.api

import com.revidd.did.data.dto.SignInQrCodeDto
import com.revidd.did.data.dto.SignInRequestDto
import com.revidd.did.data.dto.SignInResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignInApiService {

    @POST("store/v1/signin")
    suspend fun signIn(@Body signInRequestDto: SignInRequestDto): Response<SignInResponseDto>

    @GET("store/v1/generate-qr")
    suspend fun getQRCode(@Query("deviceId") deviceId: String): Response<SignInQrCodeDto>
}
