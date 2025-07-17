package com.example.data.model

import com.squareup.moshi.Json

data class ExchangeRatesResponse(
    @Json(name = "rates") val rates: Map<String, Double>,
    @Json(name = "timestamp") val timestamp: Long
)
