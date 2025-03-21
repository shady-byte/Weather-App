package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.CountryState
import com.example.weatherapp.domain.repository.StatesRepository
import com.example.weatherapp.domain.util.mapToCountryState

class GetCountryStatesUseCase(private val statesRepository: StatesRepository) {
    suspend operator fun invoke(params: String): Result<List<CountryState>> {
        return runCatching {
            statesRepository.getCountryStates(params).map { it.mapToCountryState() }
        }
    }
}