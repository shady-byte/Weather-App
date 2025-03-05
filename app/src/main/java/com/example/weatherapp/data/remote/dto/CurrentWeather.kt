package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("observation_time") val observationTime: String,
    val temperature: Int,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("weather_icons") val weatherIcons: List<String>,
    @SerializedName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerializedName("wind_speed") val windSpeed: Int,
    @SerializedName("wind_degree") val windDegree: Int,
    @SerializedName("wind_dir") val windDirection: String,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("feelslike") val feelsLike: Int,
    @SerializedName("uv_index") val uvIndex: Int,
    val visibility: Int
)
