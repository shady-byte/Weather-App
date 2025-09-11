package com.example.weatherapp.di

import com.example.core_module.di.ApiKeyProvider
import com.example.core_module.di.coreModule
import com.example.core_module.presentation.utils.STATES_API_KEY
import com.example.core_module.presentation.utils.STATES_BASE_URL
import com.example.core_module.presentation.utils.WEATHER_API_KEY
import com.example.core_module.presentation.utils.WEATHER_BASE_URL
import com.example.countrystates.di.countryStatesModule
import com.example.stateweather.di.stateWeatherModule
import com.example.weatherapp.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    includes(coreModule, countryStatesModule, stateWeatherModule)

    single(named(STATES_API_KEY)) { BuildConfig.STATES_API_KEY }
    single(named(WEATHER_API_KEY)) { BuildConfig.WEATHER_API_KEY }

    single(named(STATES_BASE_URL)) { BuildConfig.STATES_BASE_URL }
    single(named(WEATHER_BASE_URL)) { BuildConfig.WEATHER_BASE_URL }

    single {
        ApiKeyProvider(
            statesApiKey = get(named(STATES_API_KEY)),
            weatherApiKey = get(named(WEATHER_API_KEY)),
            statesBaseUrl = get(named(STATES_BASE_URL)),
            weatherBaseUrl = get(named(WEATHER_BASE_URL)),
            isDebug = BuildConfig.DEBUG
        )
    }
}