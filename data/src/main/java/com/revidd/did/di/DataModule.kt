package com.revidd.did.di

import com.revidd.did.api.VideoApiService
import com.revidd.did.repository.VideoRepositoryImpl
import com.revidd.did.repository.VideoRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(VideoApiService::class.java) }

    factory <VideoRepository> {
        VideoRepositoryImpl(
            apiService = get(),
        )
    }

}
