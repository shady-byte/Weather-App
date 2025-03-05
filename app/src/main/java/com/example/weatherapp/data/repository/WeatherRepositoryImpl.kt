package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.dto.StateWeather
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {
    override suspend fun getStateWeather(state: String): StateWeather {
        return withContext(dispatcher) {
            weatherApiService.getStateWeather(state)
        }
    }
}