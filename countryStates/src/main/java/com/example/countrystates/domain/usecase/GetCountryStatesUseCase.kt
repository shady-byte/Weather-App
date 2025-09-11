package com.example.countrystates.domain.usecase

import com.example.countrystates.domain.model.CountryState
import com.example.countrystates.domain.repository.StatesRepository
import com.example.countrystates.data.mapper.mapToCountryState

class GetCountryStatesUseCase(private val statesRepository: StatesRepository) {
    suspend operator fun invoke(params: String): Result<List<CountryState>> {
        return runCatching {
            statesRepository.getCountryStates(params).map { it.mapToCountryState() }
        }
    }
}