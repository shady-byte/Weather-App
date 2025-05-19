package com.example.presentation.navigation

object NavigationRoutes {
    const val STATES = "states"
    const val WEATHER = "weather/{state}"

    fun weatherRoute(state: String) = "weather/$state"
}