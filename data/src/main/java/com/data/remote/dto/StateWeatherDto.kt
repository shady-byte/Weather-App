package com.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StateWeatherDto(
    @SerialName("location")val locationInfo: LocationInfoDto,
    @SerialName("current") val currentWeather: CurrentWeatherDto
)
