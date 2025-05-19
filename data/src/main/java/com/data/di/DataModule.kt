package com.data.di

import com.data.remote.api.StatesApiService
import com.data.remote.api.WeatherApiService
import com.data.repository.StatesRepositoryImpl
import com.data.repository.WeatherRepositoryImpl
import com.example.data.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import domain.repository.StatesRepository
import domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor

private const val STATES_CLIENT = "states_client"
private const val WEATHER_CLIENT = "weather_client"

@OptIn(ExperimentalSerializationApi::class)
val dataModule = module {

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    single(named(STATES_CLIENT)) {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG)
            builder.addInterceptor(logging)

        builder.addInterceptor { chain ->
            val original = chain.request()
            val newRequest = original.newBuilder()
                .header("X-CSCAPI-KEY", BuildConfig.STATES_API_KEY)
                .build()
            chain.proceed(newRequest)
        }.build()
    }

    single(named(WEATHER_CLIENT)) {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG)
            builder.addInterceptor(logging)

        builder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("access_key", BuildConfig.WEATHER_API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.STATES_BASE_URL)
            .client(get(named(STATES_CLIENT)))
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create(StatesApiService::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_BASE_URL)
            .client(get(named(WEATHER_CLIENT)))
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create(WeatherApiService::class.java)
    }

    single<CoroutineDispatcher> { Dispatchers.IO }

    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    single<StatesRepository> { StatesRepositoryImpl(get(), get()) }
}