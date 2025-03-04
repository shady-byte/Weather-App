package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.model.StateWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.ResultState

class GetStateWeatherUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<String, StateWeather> {
    override suspend fun invoke(params: String): ResultState<StateWeather> {
        return try {
            val response = weatherRepository.getStateWeather(params)
            ResultState.Success(response)
        } catch (ex: Exception) {
            ResultState.Error(ex.message ?: "An error occurred")
        }
    }
}