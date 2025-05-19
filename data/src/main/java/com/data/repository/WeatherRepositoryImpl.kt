package com.data.repository

import com.data.remote.api.WeatherApiService
import com.data.remote.mapper.mapToStateWeather
import domain.model.StateWeather
import domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {
    override suspend fun getStateWeather(state: String): StateWeather {
        return withContext(dispatcher) {
            weatherApiService.getStateWeather(state).mapToStateWeather()
        }
    }
}