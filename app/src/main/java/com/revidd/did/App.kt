package com.revidd.did

import android.app.Application
import com.revidd.did.data.di.dataModule
import com.revidd.did.di.domainModule
import com.revidd.did.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(presentationModule ,dataModule, domainModule)
            )
        }
    }
}
