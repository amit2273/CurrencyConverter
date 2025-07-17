package com.example.data.api

import com.example.data.model.ExchangeRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {
    @GET("latest.json")
    suspend fun getLatestRates(
        @Query("app_id") appId: String
    ): Response<ExchangeRatesResponse>

    @GET("currencies.json")
    suspend fun getCurrencies(): Response<Map<String, String>>
}