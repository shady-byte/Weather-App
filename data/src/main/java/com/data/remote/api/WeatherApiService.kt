package com.data.remote.api

import com.data.remote.dto.StateWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current")
    suspend fun getStateWeather(
        @Query("query") state: String,
    ): StateWeatherDto
}