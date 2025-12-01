package com.example.currencyconverter

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import di.currencyConverterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(currencyConverterModule, dataModule, domainModule)
            )
        }
    }
}
