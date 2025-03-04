package com.example.weatherapp.data.remote

import com.example.weatherapp.data.model.CountryState
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StatesApiService {
    @GET("countries/{countryCode}/states")
    suspend fun getCountryStates(
        @Path("countryCode") countryCode: String,
        @Header("X-CSCAPI-KEY") apiKey: String
    ): List<CountryState>
}