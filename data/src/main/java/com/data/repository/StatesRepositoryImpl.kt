package com.data.repository

import com.data.remote.api.StatesApiService
import com.data.remote.mapper.mapToCountryState
import domain.model.CountryState
import domain.repository.StatesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StatesRepositoryImpl(
    private val statesApiService: StatesApiService,
    private val dispatcher: CoroutineDispatcher
) : StatesRepository {
    override suspend fun getCountryStates(countryCode: String): List<CountryState> {
        return withContext(dispatcher) {
            statesApiService.getCountryStates(countryCode).map { it.mapToCountryState() }
        }
    }
}