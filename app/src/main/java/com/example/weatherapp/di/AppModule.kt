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
import com.example.weatherapp.viewModel.StatesViewModel
import com.example.weatherapp.viewModel.WeatherViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { OkHttpClient.Builder().build() }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.STATES_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StatesApiService::class.java)
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
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