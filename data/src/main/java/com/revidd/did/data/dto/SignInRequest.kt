package com.revidd.did.data.dto

data class SignInRequest(
    val email : String,
    val password : String,
    val storefront : String,
    val deviceId : String,
    val deviceMeta : DeviceMetaData,
    )


data class DeviceMetaData(private val brand : String,
    private val sdkVersion : String,
    private val model : String,
    private val isApp : Boolean = false,
    private val isTv : Boolean = true
)
