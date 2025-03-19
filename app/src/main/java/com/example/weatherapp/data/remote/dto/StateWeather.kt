package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StateWeather(
    val location: LocationInfo,
    @SerialName("current") val currentWeather: CurrentWeather
)
