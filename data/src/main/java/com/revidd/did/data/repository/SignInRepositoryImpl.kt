package com.revidd.did.data.repository

import com.revidd.did.data.api.SignInApiService
import com.revidd.did.data.dto.DeviceMetaData
import com.revidd.did.data.dto.SignInRequestDto
import com.revidd.did.model.SignInData
import com.revidd.did.repository.SignInRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.io.File

class SignInRepositoryImpl(private val dispatcher : CoroutineDispatcher,
                           private val signInApiService: SignInApiService) : SignInRepository {
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

    override suspend fun getQRCode(deviceId: String): Result<String> {
        val response = signInApiService.getQRCode(deviceId)

        if (!response.isSuccessful || response.body() == null) {
            return Result.failure(Throwable("QR API failed"))
        }

        val rawQr = response.body()!!.qrCode

        return Result.success(
            if (rawQr.startsWith("data:image")) {
                base64ToFileUri(rawQr)
            } else {
                rawQr
            }
        )
    }

    private suspend fun base64ToFileUri(dataUrl: String) =
        withContext(dispatcher){
            val clean = dataUrl.substringAfter("base64,", "")
            val bytes = android.util.Base64.decode(clean, android.util.Base64.DEFAULT)

            val file = File.createTempFile("qr_", ".png")
            file.writeBytes(bytes)

            file.toURI().toString()
        }


}