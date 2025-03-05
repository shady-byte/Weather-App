package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.dto.StateWeather

interface WeatherRepository {
    suspend fun getStateWeather(state: String): StateWeather
}