package com.example.stateweather.presentation.mapper

import android.content.Context
import com.example.stateweather.R
import com.example.stateweather.domain.model.StateWeather
import com.example.stateweather.domain.model.WeatherDetail

fun StateWeather.getWeatherDetails(context: Context): List<WeatherDetail> {
    return listOf(
        WeatherDetail(
            titleRes = R.string.uv_index,
            subtitle = currentWeather.uvIndex,
            imageSrc = R.drawable.uv_index_icon
        ),
        WeatherDetail(
            titleRes = R.string.humidity,
            subtitle = context.getString(
                R.string.humidity_degree,
                currentWeather.humidity
            ),
            imageSrc = R.drawable.humidity_icon
        ),
        WeatherDetail(
            titleRes = R.string.wind_speed,
            subtitle = context.getString(
                R.string.wind_speed_degree,
                currentWeather.windSpeed
            ),
            imageSrc = R.drawable.wind_speed_icon
        ),
        WeatherDetail(
            titleRes = R.string.visibility,
            subtitle = context.getString(R.string.visibility_degree, currentWeather.visibility),
            imageSrc = R.drawable.visibility_icon
        ),
        WeatherDetail(
            titleRes = R.string.pressure,
            subtitle = context.getString(
                R.string.pressure_degree,
                currentWeather.pressure
            ),
            imageSrc = R.drawable.pressure_icon
        ),
        WeatherDetail(
            titleRes = R.string.wind_direction,
            subtitle = currentWeather.windDirection,
            imageSrc = R.drawable.wind_direction_icon
        )
    )
}
