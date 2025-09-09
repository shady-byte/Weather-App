package com.example.weatherapp.di

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.StatesApiService
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.data.repository.StatesRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.StatesRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GetCountryStatesUseCase
import com.example.weatherapp.domain.usecase.GetStateWeatherUseCase
import com.example.weatherapp.ui.viewModel.StatesViewModel
import com.example.weatherapp.ui.viewModel.WeatherViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor

const val STATES_CLIENT = "states_client"
const val WEATHER_CLIENT = "weather_client"

@OptIn(ExperimentalSerializationApi::class)
val appModule = module {

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

    factory { GetStateWeatherUseCase(get()) }
    factory { GetCountryStatesUseCase(get()) }

    viewModel { WeatherViewModel(get()) }
    viewModel { StatesViewModel(get()) }
}