package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RateDao {
    @Query("SELECT * FROM rates")
    suspend fun getAll(): List<RateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rates: List<RateEntity>)

    @Query("DELETE FROM rates")
    suspend fun clearAll()
}