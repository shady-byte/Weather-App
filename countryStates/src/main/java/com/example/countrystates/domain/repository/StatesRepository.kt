package com.example.countrystates.domain.repository

interface StatesRepository {
    suspend fun getCountryStates(countryCode: String): List<com.example.countrystates.data.remote.dto.CountryStateDto>
}