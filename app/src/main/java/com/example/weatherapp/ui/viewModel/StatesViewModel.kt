package com.example.weatherapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.CountryState
import com.example.weatherapp.domain.usecase.GetCountryStatesUseCase
import com.example.weatherapp.ui.util.StatesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatesViewModel(private val getCountryStatesUseCase: GetCountryStatesUseCase) : ViewModel() {
    private val _uiState =
        MutableStateFlow<StatesUiState<List<CountryState>>>(StatesUiState.Idle)
    val uiState: StateFlow<StatesUiState<List<CountryState>>> = _uiState

    fun getCountryStates(countryCode: String) {
        viewModelScope.launch {
            _uiState.emit(StatesUiState.Loading)
            getCountryStatesUseCase(countryCode)
                .onSuccess {
                    if (it.isNotEmpty())
                        _uiState.emit(StatesUiState.Success(it))
                    else
                        _uiState.emit(StatesUiState.Error(R.string.no_states))
                }
                .onFailure { _uiState.emit(StatesUiState.Error(R.string.no_iso2_error)) }
        }
    }
}