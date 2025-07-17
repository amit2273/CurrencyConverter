package com.example.data

import android.content.Context
import androidx.room.Room
import com.example.data.api.ExchangeRatesApi
import com.example.data.db.AppDatabase
import com.example.data.repository.CurrencyRepositoryImpl
import com.example.data.util.PrefsLastUpdated
import com.example.domain.repository.CurrencyRepository
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
        Room.databaseBuilder(get(), AppDatabase::class.java, "currency_db").build()
    }

    single { get<AppDatabase>().rateDao() }

    single {
        PrefsLastUpdated(
            get<Context>().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        )
    }

    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            api = get(),
            dao = get(),
            appId = "YOUR_APP_ID",
            getTime = { System.currentTimeMillis() / 1000 },
            getLastUpdated = { get<PrefsLastUpdated>().get() },
            setLastUpdated = { get<PrefsLastUpdated>().set(it) }
        )
    }
}
