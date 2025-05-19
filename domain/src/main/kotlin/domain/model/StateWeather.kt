package domain.model

data class StateWeather(
    val location: LocationInfo,
    val currentWeather: CurrentWeather
)
