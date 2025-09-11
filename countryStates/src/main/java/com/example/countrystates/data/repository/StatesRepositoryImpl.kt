package com.example.countrystates.data.repository

import com.example.countrystates.data.remote.StatesApiService
import com.example.countrystates.domain.repository.StatesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StatesRepositoryImpl(
    private val statesApiService: StatesApiService,
    private val dispatcher: CoroutineDispatcher
) : StatesRepository {
    override suspend fun getCountryStates(countryCode: String): List<com.example.countrystates.data.remote.dto.CountryStateDto> {
        return withContext(dispatcher) {
            statesApiService.getCountryStates(countryCode)
        }
    }
}