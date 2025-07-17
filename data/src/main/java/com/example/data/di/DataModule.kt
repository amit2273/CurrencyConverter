package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.data.api.ExchangeRatesApi
import com.example.data.db.AppDatabase
import com.example.data.preference.PrefsLastUpdated
import com.example.data.repository.CurrencyRepositoryImpl
import com.example.domain.repository.CurrencyRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ExchangeRatesApi::class.java) }

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "currency_db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<AppDatabase>().rateDao() }

    single { get<AppDatabase>().currencyDao() }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    single { PrefsLastUpdated(get()) }

    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            api = get(),
            dao = get(),
            currencyDao = get(),
            appId = "2116d3aa7d2d462b82ed5e33b3014c6c",
            prefs = get()
        )
    }
}
