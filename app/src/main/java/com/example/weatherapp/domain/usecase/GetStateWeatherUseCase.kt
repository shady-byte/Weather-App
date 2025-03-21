package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.StateWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.mapToStateWeather

class GetStateWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(params: String): Result<StateWeather> {
        return runCatching {
            weatherRepository.getStateWeather(params).mapToStateWeather()
        }
    }
}