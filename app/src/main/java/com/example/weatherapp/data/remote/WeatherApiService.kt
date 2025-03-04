package com.example.weatherapp.data.remote

import com.example.weatherapp.data.model.StateWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current")
    suspend fun getStateWeather(
        @Query("query") state: String,
        @Query("access_key") apiKey: String
    ): StateWeather
}