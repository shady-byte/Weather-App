package com.example.stateweather.data.repository

import com.example.stateweather.data.remote.WeatherApiService
import com.example.stateweather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {
    override suspend fun getStateWeather(state: String): com.example.stateweather.data.remote.dto.StateWeatherDto {
        return withContext(dispatcher) {
            weatherApiService.getStateWeather(state)
        }
    }
}