package com.example.weatherapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.StateWeather
import com.example.weatherapp.domain.usecase.GetStateWeatherUseCase
import com.example.weatherapp.ui.util.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val getStateWeatherUseCase: GetStateWeatherUseCase) : ViewModel() {
    private val _uiState =
        MutableStateFlow<WeatherUiState<StateWeather>>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState<StateWeather>> = _uiState

    fun getStateWeather(state: String) {
        viewModelScope.launch {
            getStateWeatherUseCase(state)
                .onSuccess { _uiState.emit(WeatherUiState.Success(it)) }
                .onFailure { _uiState.emit(WeatherUiState.Error(it.message ?: "An error occurred")) }
        }
    }
}