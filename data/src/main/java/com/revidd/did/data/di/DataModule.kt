package com.revidd.did.data.di

import com.revidd.did.data.api.SignInApiService
import com.revidd.did.data.api.VideoApiService
import com.revidd.did.data.repository.SignInRepositoryImpl
import com.revidd.did.data.repository.VideoRepositoryImpl
import com.revidd.did.repository.SignInRepository
import com.revidd.did.repository.VideoRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://ultraplays.api1.revidd.video/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(VideoApiService::class.java) }

    single { get<Retrofit>().create(SignInApiService::class.java) }

    factory <VideoRepository> {
        VideoRepositoryImpl(
            apiService = get(),
        )
    }

    factory <SignInRepository> {
        SignInRepositoryImpl(
            signInApiService = get(),
        )
    }

}
