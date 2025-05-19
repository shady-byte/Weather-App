package domain.usecase

import domain.model.StateWeather
import domain.repository.WeatherRepository

class GetStateWeatherUseCaseImpl(private val weatherRepository: WeatherRepository) :
    GetStateWeatherUseCase {
    override suspend operator fun invoke(params: String): Result<StateWeather> {
        return runCatching {
            weatherRepository.getStateWeather(params)
        }
    }
}