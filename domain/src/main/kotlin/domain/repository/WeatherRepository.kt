package domain.repository

import domain.model.StateWeather

interface WeatherRepository {
    suspend fun getStateWeather(state: String): StateWeather
}