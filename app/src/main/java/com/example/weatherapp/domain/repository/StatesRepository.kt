package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.dto.CountryStateDto

interface StatesRepository {
    suspend fun getCountryStates(countryCode: String): List<CountryStateDto>
}