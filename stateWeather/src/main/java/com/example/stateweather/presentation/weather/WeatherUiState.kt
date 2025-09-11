package com.example.stateweather.presentation.weather

sealed class WeatherUiState<out T> {
    data object Loading: WeatherUiState<Nothing>()
    data class Success<T>(val data: T): WeatherUiState<T>()
    data class Error(val message: String): WeatherUiState<Nothing>()
}