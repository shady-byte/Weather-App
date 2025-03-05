package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StateWeather(
    val location: LocationInfo,
    @SerializedName("current") val currentWeather: CurrentWeather
)
