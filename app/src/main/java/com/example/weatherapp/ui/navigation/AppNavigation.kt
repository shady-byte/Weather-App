package com.example.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.ui.screen.StateWeatherScreen
import com.example.weatherapp.ui.screen.StatesScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "states") {
        composable("states") {
            StatesScreen(navController = navController)
        }

        composable("weather/{state}") { backStackEntry ->
            val state = backStackEntry.arguments?.getString("state") ?: ""
            StateWeatherScreen(navController = navController, countryState = state)
        }
    }
}