package com.example.presentation.di

import com.example.presentation.features.countryStates.StatesViewModel
import com.example.presentation.features.stateWeather.WeatherViewModel
import domain.usecase.GetCountryStatesUseCase
import domain.usecase.GetCountryStatesUseCaseImpl
import domain.usecase.GetStateWeatherUseCase
import domain.usecase.GetStateWeatherUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    factory<GetStateWeatherUseCase> { GetStateWeatherUseCaseImpl(get()) }
    factory<GetCountryStatesUseCase> { GetCountryStatesUseCaseImpl(get()) }

    viewModel { WeatherViewModel(get()) }
    viewModel { StatesViewModel(get()) }
}