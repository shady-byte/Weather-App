package com.example.stateweather.data.mapper

import com.example.stateweather.data.remote.dto.CurrentWeatherDto
import com.example.stateweather.data.remote.dto.LocationInfoDto
import com.example.stateweather.domain.model.CurrentWeather
import com.example.stateweather.domain.model.LocationInfo
import com.example.stateweather.domain.model.StateWeather

fun com.example.stateweather.data.remote.dto.StateWeatherDto.mapToStateWeather(): StateWeather = StateWeather(
    location = locationInfo.mapToLocationInfo(),
    currentWeather = currentWeather.mapToCurrentWeather()
)

private fun LocationInfoDto.mapToLocationInfo(): LocationInfo = LocationInfo(
    country = country
)

private fun CurrentWeatherDto.mapToCurrentWeather(): CurrentWeather = CurrentWeather(
    observationTime = observationTime,
    temperature = temperature,
    weatherIcon = weatherIcons.first(),
    windSpeed = windSpeed.toString(),
    windDirection = windDirection,
    pressure = pressure.toString(),
    humidity = humidity.toString(),
    feelsLike = feelsLike.toString(),
    uvIndex = uvIndex.toString(),
    visibility = visibility.toString()
)