package com.revidd.did.data.repository

import com.revidd.did.data.api.SignInApiService
import com.revidd.did.data.dto.DeviceMetaData
import com.revidd.did.data.dto.SignInRequestDto
import com.revidd.did.model.SignInData
import com.revidd.did.repository.SignInRepository

class SignInRepositoryImpl(private val signInApiService: SignInApiService) : SignInRepository {
    override suspend fun signIn(data : SignInData): Result<Boolean> {
        val response = signInApiService.signIn(signInRequestDto = SignInRequestDto(email = data.email,
            password = data.password,
            storefront = "",
            deviceId = "",
            deviceMeta = DeviceMetaData(
                brand = "",
                sdkVersion = "",
                model = "",
                isApp =  false,
                isTv = true
            )
        ))
        if(response.isSuccessful && response.body() != null){
            return  Result.success(true)
        }else{
            return Result.failure(Throwable())
        }
    }
}