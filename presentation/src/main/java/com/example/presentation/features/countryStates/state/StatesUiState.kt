package com.example.presentation.features.countryStates.state

sealed class StatesUiState<out T> {
    data object Idle: StatesUiState<Nothing>()
    data object Loading: StatesUiState<Nothing>()
    data class Success<T>(val data: T): StatesUiState<T>()
    data class Error(val messageRes: Int): StatesUiState<Nothing>()
}