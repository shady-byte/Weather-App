package com.data.remote.mapper

import com.data.remote.dto.CountryStateDto
import com.data.remote.dto.CurrentWeatherDto
import com.data.remote.dto.LocationInfoDto
import com.data.remote.dto.StateWeatherDto
import domain.model.CountryState
import domain.model.CurrentWeather
import domain.model.LocationInfo
import domain.model.StateWeather

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