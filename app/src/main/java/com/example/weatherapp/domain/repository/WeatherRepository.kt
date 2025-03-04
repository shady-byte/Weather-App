package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.model.StateWeather

interface WeatherRepository {
    suspend fun getStateWeather(state: String): StateWeather
}