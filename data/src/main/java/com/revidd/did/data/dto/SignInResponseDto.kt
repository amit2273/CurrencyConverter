package com.revidd.did.data.dto

data class SignInResponseDto(
    val success: Boolean,
    val accessToken: String,
    val expiresAt: String,
    val refreshToken: String,
    val refreshTokenExpiresAt: String,
    val isDisabled: Boolean,
    val provider: String,
    val verifiedAt: String?
)