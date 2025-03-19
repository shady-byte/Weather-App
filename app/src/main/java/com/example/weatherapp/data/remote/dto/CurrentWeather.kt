package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    @SerialName("observation_time") val observationTime: String,
    val temperature: Int,
    @SerialName("weather_code") val weatherCode: Int,
    @SerialName("weather_icons") val weatherIcons: List<String>,
    @SerialName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerialName("wind_speed") val windSpeed: Int,
    @SerialName("wind_degree") val windDegree: Int,
    @SerialName("wind_dir") val windDirection: String,
    val pressure: Int,
    val humidity: Int,
    @SerialName("feelslike") val feelsLike: Int,
    @SerialName("uv_index") val uvIndex: Int,
    val visibility: Int
)
