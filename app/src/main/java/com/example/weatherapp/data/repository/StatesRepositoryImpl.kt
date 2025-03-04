package com.example.weatherapp.data.repository

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.model.CountryState
import com.example.weatherapp.data.remote.StatesApiService
import com.example.weatherapp.domain.repository.StatesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StatesRepositoryImpl(
    private val statesApiService: StatesApiService,
    private val dispatcher: CoroutineDispatcher
) : StatesRepository {
    private val apiKey = BuildConfig.STATES_API_KEY

    override suspend fun getCountryStates(countryCode: String): List<CountryState> {
        return withContext(dispatcher) {
            statesApiService.getCountryStates(countryCode, apiKey)
        }
    }
}