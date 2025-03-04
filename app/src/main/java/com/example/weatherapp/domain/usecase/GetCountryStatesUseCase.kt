package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.model.CountryState
import com.example.weatherapp.domain.repository.StatesRepository
import com.example.weatherapp.domain.util.ResultState

class GetCountryStatesUseCase(private val statesRepository: StatesRepository) :
    UseCase<String, List<CountryState>> {
    override suspend fun invoke(params: String): ResultState<List<CountryState>> {
        return try {
            val response = statesRepository.getCountryStates(params)
            ResultState.Success(response)
        } catch (ex: Exception) {
            ResultState.Error(ex.message ?: "An error occurred")
        }
    }
}