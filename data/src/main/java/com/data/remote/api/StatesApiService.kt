package com.data.remote.api

import com.data.remote.dto.CountryStateDto
import retrofit2.http.GET
import retrofit2.http.Path

interface StatesApiService {
    @GET("countries/{countryCode}/states")
    suspend fun getCountryStates(
        @Path("countryCode") countryCode: String,
    ): List<CountryStateDto>
}