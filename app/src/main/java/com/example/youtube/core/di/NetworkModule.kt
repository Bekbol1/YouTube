package com.example.youtube.core.di

import com.example.youtube.core.network.provideApiService
import com.example.youtube.core.network.provideInterceptor
import com.example.youtube.core.network.provideOkHttpClient
import com.example.youtube.core.network.provideRetrofit
import org.koin.dsl.module

val networkModule = module {
    single { provideInterceptor() }
    single { provideOkHttpClient(get()) }
    factory { provideRetrofit(get()) }
    single { provideApiService(get()) }
}