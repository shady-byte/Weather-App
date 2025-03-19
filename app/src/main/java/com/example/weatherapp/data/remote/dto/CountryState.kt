package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CountryState(
    val id: Int,
    val name: String,
    val iso2: String
)
