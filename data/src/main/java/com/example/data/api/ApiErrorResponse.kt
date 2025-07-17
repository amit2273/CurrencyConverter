package com.example.data.api

data class ApiErrorResponse(
    val error: Boolean,
    val status: Int,
    val message: String,
    val description: String
)