package com.example.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core_module.presentation.navigation.AppScreen
import com.example.core_module.presentation.navigation.StatesNavigator
import com.example.core_module.presentation.navigation.WeatherNavigator
import com.example.core_module.presentation.utils.STATE
import com.example.countrystates.presentation.states.StatesScreen
import com.example.stateweather.presentation.weather.StateWeatherScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreen.States.route) {
        composable(AppScreen.States.route) {
            StatesScreen(navigator = rememberStatesNavigator(navController))
        }

        composable(
            route = AppScreen.Weather.route,
            arguments = listOf(navArgument(STATE) { type = NavType.StringType })
        ) { backStackEntry ->
            val state = backStackEntry.arguments?.getString(STATE) ?: ""

            StateWeatherScreen(
                navigator = rememberWeatherNavigator(navController),
                countryState = state
            )
        }
    }
}

@Composable
fun rememberStatesNavigator(navController: NavHostController): StatesNavigator {
    return remember(navController) {
        object : StatesNavigator {
            override fun navigateToWeather(state: String) {
                navController.navigate(AppScreen.Weather.createRoute(state))
            }
            override fun popBackStack() {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun rememberWeatherNavigator(navController: NavHostController): WeatherNavigator {
    return remember(navController) {
        object : WeatherNavigator {
            override fun popBackStack() {
                navController.popBackStack()
            }
        }
    }
}