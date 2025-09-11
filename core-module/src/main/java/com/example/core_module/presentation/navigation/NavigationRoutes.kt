package com.example.core_module.presentation.navigation

import com.example.core_module.presentation.utils.STATE
import com.example.core_module.presentation.utils.STATES
import com.example.core_module.presentation.utils.WEATHER

sealed class AppScreen(val route: String) {
    data object States : AppScreen(STATES)
    data object Weather : AppScreen("$WEATHER/{$STATE}") {
        fun createRoute(state: String) = "$WEATHER/$state"
    }
}

interface StatesNavigator {
    fun navigateToWeather(state: String)
    fun popBackStack()
}

interface WeatherNavigator {
    fun popBackStack()
}