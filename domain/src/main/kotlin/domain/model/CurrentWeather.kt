package domain.model

data class CurrentWeather(
    val observationTime: String,
    val temperature: Int,
    val weatherIcon: String,
    val windSpeed: String,
    val windDirection: String,
    val pressure: String,
    val humidity: String,
    val feelsLike: String,
    val uvIndex: String,
    val visibility: String
)
