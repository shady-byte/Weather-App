package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.dto.StateWeatherDto

interface WeatherRepository {
    suspend fun getStateWeather(state: String): StateWeatherDto
}