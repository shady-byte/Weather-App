package com.example.weatherapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.remote.dto.StateWeather
import com.example.weatherapp.domain.usecase.GetStateWeatherUseCase
import com.example.weatherapp.domain.util.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val getStateWeatherUseCase: GetStateWeatherUseCase) : ViewModel() {
    private val _uiState =
        MutableStateFlow<ResultState<StateWeather>>(ResultState.Loading)
    val uiState: StateFlow<ResultState<StateWeather>> = _uiState

    fun getStateWeather(state: String) {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            _uiState.value = getStateWeatherUseCase(state)
        }
    }
}