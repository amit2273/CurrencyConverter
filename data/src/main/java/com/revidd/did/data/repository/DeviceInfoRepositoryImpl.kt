package com.revidd.did.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.revidd.did.repository.DeviceInfoRepository

internal  class DeviceInfoRepositoryImpl(private val context: Context) : DeviceInfoRepository {
    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    override fun getDeviceModel(): Result<String> = kotlin.runCatching {
        Build.MODEL
    }


    override fun getOsVersion(): Result<String> = kotlin.runCatching {
        Build.VERSION.RELEASE
    }

}