package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationInfo(
    val name: String,
    val country: String,
    @SerialName("timezone_id") val timezoneId: String
)