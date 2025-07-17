package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RateEntity::class, CurrencyEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rateDao(): RateDao
    abstract fun currencyDao(): CurrencyDao
}
