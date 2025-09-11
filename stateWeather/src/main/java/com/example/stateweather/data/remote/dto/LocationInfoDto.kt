package com.example.stateweather.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationInfoDto(
    val name: String,
    val country: String,
    @SerialName("timezone_id") val timezoneId: String
)