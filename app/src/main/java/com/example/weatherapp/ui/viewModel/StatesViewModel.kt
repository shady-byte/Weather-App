package com.example.weatherapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.remote.dto.CountryState
import com.example.weatherapp.domain.usecase.GetCountryStatesUseCase
import com.example.weatherapp.domain.util.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatesViewModel(private val getCountryStatesUseCase: GetCountryStatesUseCase) : ViewModel() {
    private val _uiState =
        MutableStateFlow<ResultState<List<CountryState>>>(ResultState.Idle)
    val uiState: StateFlow<ResultState<List<CountryState>>> = _uiState

    fun getCountryStates(countryCode: String) {
        viewModelScope.launch {
            _uiState.emit(ResultState.Loading)
            _uiState.emit(getCountryStatesUseCase(countryCode))
        }
    }
}