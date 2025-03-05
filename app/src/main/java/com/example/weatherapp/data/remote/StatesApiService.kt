package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.CountryState
import retrofit2.http.GET
import retrofit2.http.Path

interface StatesApiService {
    @GET("countries/{countryCode}/states")
    suspend fun getCountryStates(
        @Path("countryCode") countryCode: String,
    ): List<CountryState>
}