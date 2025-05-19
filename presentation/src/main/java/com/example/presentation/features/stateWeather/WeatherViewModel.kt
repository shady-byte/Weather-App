package com.example.presentation.features.stateWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.features.stateWeather.state.WeatherUiState
import domain.model.StateWeather
import domain.usecase.GetStateWeatherUseCase
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