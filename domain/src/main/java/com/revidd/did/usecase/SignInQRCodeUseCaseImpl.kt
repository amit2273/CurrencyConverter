package com.revidd.did.usecase

import com.revidd.did.repository.DeviceInfoRepository
import com.revidd.did.repository.SignInRepository

class SignInQRCodeUseCaseImpl(private val repository: SignInRepository,
    private  val deviceInfoRepository: DeviceInfoRepository) : SignInQRCodeUseCase {


    override suspend fun execute(inputT: Unit): Result<String> =
        repository.getQRCode(deviceInfoRepository.getDeviceId())

}