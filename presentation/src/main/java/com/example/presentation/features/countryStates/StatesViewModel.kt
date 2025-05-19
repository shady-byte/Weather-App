package com.example.presentation.features.countryStates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.R
import com.example.presentation.features.countryStates.state.StatesUiState
import domain.model.CountryState
import domain.usecase.GetCountryStatesUseCase
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