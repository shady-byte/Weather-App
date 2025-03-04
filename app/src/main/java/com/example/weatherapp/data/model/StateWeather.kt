package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class StateWeather(
    val location: LocationInfo,
    @SerializedName("current") val currentWeather: CurrentWeather
)
