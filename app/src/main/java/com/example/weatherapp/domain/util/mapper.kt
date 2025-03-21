package com.example.weatherapp.domain.util

import com.example.weatherapp.data.remote.dto.CountryStateDto
import com.example.weatherapp.data.remote.dto.CurrentWeatherDto
import com.example.weatherapp.data.remote.dto.LocationInfoDto
import com.example.weatherapp.data.remote.dto.StateWeatherDto
import com.example.weatherapp.domain.model.CountryState
import com.example.weatherapp.domain.model.CurrentWeather
import com.example.weatherapp.domain.model.LocationInfo
import com.example.weatherapp.domain.model.StateWeather

fun CountryStateDto.mapToCountryState(): CountryState = CountryState(
    name = name,
    iso2 = iso2
)

fun StateWeatherDto.mapToStateWeather(): StateWeather = StateWeather(
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