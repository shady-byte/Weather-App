package com.example.stateweather.domain.usecase

import com.example.stateweather.domain.model.StateWeather
import com.example.stateweather.data.mapper.mapToStateWeather

class GetStateWeatherUseCase(private val weatherRepository: com.example.stateweather.domain.repository.WeatherRepository) {
    suspend operator fun invoke(params: String): Result<StateWeather> {
        return runCatching {
            weatherRepository.getStateWeather(params).mapToStateWeather()
        }
    }
}