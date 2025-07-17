package com.example.data.preference

import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsLastUpdated(private val prefs: SharedPreferences) {

    fun get(key: String): Long {
        return prefs.getLong(key, 0L)
    }

    fun set(key: String, value: Long) {
        prefs.edit {
            putLong(key, value)
        }
    }

    companion object {
        const val KEY_CURRENCY_LIST = "last_updated_currency_list"
        const val KEY_EXCHANGE_RATES = "last_updated_exchange_rates"
    }
}
