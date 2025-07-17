package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class RateEntity(
    @PrimaryKey val currencyCode: String,
    val rateAgainstUSD: Double
)
