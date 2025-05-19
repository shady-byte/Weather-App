package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.presentation.features.countryStates.ui.screen.StatesScreen
import com.example.presentation.features.stateWeather.ui.screen.StateWeatherScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoutes.STATES) {
        composable(NavigationRoutes.STATES) {
            StatesScreen(navController = navController)
        }

        composable(NavigationRoutes.WEATHER) { backStackEntry ->
            val state = backStackEntry.arguments?.getString("state") ?: ""
            StateWeatherScreen(navController = navController, countryState = state)
        }
    }
}