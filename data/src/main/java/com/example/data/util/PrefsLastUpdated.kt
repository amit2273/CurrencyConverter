package com.example.data.util

import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsLastUpdated(private val prefs: SharedPreferences) {
    suspend fun get(): Long = prefs.getLong("last_updated", 0L)
    suspend fun set(value: Long) = prefs.edit { putLong("last_updated", value) }
}
