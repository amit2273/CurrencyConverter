package com.revidd.did.repository

interface DeviceInfoRepository{
    fun getDeviceId(): String
    fun getDeviceModel(): Result<String>
    fun getOsVersion(): Result<String>
}