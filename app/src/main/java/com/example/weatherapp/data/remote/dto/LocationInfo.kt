package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationInfo(
    val name: String,
    val country: String,
    @SerializedName("timezone_id") val timezoneId: String
)