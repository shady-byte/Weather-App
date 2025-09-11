package com.example.stateweather.domain.repository

interface WeatherRepository {
    suspend fun getStateWeather(state: String): com.example.stateweather.data.remote.dto.StateWeatherDto
}