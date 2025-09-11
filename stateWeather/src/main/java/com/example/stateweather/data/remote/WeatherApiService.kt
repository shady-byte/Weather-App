package com.example.stateweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current")
    suspend fun getStateWeather(
        @Query("query") state: String,
    ): com.example.stateweather.data.remote.dto.StateWeatherDto
}