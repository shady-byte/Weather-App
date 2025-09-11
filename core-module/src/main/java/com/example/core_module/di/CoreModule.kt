package com.example.core_module.di

import com.example.core_module.presentation.utils.DEFAULT_CONFIG
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named

val coreModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            level = if (get<ApiKeyProvider>().isDebug)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

    single(named(DEFAULT_CONFIG)) {
        OkHttpClient.Builder().apply {
            addInterceptor(get<HttpLoggingInterceptor>())
        }.build()
    }

    single<CoroutineDispatcher> { Dispatchers.IO }
}