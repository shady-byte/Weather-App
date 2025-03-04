package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.model.CountryState

interface StatesRepository {
    suspend fun getCountryStates(countryCode: String): List<CountryState>
}