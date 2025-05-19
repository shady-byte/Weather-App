package domain.usecase

import domain.model.StateWeather

interface GetStateWeatherUseCase {
    suspend operator fun invoke(params: String): Result<StateWeather>
}