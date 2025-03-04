package com.example.weatherapp.data.repository

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.model.StateWeather
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {
    private val apiKey = BuildConfig.WEATHER_API_KEY

    override suspend fun getStateWeather(state: String): StateWeather {
        return withContext(dispatcher) {
            weatherApiService.getStateWeather(state, apiKey)
        }
    }
}