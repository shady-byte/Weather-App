package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.dto.CountryStateDto
import com.example.weatherapp.data.remote.StatesApiService
import com.example.weatherapp.domain.repository.StatesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StatesRepositoryImpl(
    private val statesApiService: StatesApiService,
    private val dispatcher: CoroutineDispatcher
) : StatesRepository {
    override suspend fun getCountryStates(countryCode: String): List<CountryStateDto> {
        return withContext(dispatcher) {
            statesApiService.getCountryStates(countryCode)
        }
    }
}