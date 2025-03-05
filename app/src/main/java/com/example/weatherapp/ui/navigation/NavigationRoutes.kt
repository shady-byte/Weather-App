package com.example.weatherapp.ui.navigation

object NavigationRoutes {
    const val STATES = "states"
    const val WEATHER = "weather/{state}"

    fun weatherRoute(state: String) = "weather/$state"
}