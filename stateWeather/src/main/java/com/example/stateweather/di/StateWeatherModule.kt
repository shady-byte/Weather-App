package com.example.stateweather.di

import com.example.core_module.di.ApiKeyProvider
import com.example.core_module.presentation.utils.DEFAULT_CONFIG
import com.example.stateweather.data.remote.WeatherApiService
import com.example.stateweather.domain.repository.WeatherRepository
import com.example.stateweather.domain.usecase.GetStateWeatherUseCase
import com.example.stateweather.presentation.weather.WeatherViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val WEATHER_CLIENT = "weather_client"

@OptIn(ExperimentalSerializationApi::class)
val stateWeatherModule = module {

    single(named(WEATHER_CLIENT)) {
        get<OkHttpClient>(named(DEFAULT_CONFIG)).newBuilder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("access_key", get<ApiKeyProvider>().weatherApiKey)
                .build()

            val newRequest = originalRequest.newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<ApiKeyProvider>().weatherBaseUrl)
            .client(get(named(WEATHER_CLIENT)))
            .addConverterFactory(
                get<Json>().asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create(WeatherApiService::class.java)
    }

    single<WeatherRepository> {
        com.example.stateweather.data.repository.WeatherRepositoryImpl(
            get(),
            get()
        )
    }

    factory { GetStateWeatherUseCase(get()) }

    viewModel { WeatherViewModel(get()) }
}