package com.example.weatherapp.domain

import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.model.LocationInfo
import com.example.weatherapp.data.model.StateWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GetStateWeatherUseCase
import com.example.weatherapp.domain.util.ResultState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetStateWeatherUseCaseUnitTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val weatherRepository: WeatherRepository = mockk()

    private lateinit var getStateWeatherUseCase: GetStateWeatherUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getStateWeatherUseCase = GetStateWeatherUseCase(weatherRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should return Success when repository returns data`() = runTest {
        val countryCode = "EG"
        val expectedWeather = StateWeather(
            location = LocationInfo(
                name = "Alexandria",
                country = "Egypt",
                timezoneId = "4"
            ),
            currentWeather = CurrentWeather(
                observationTime = "03:15PM",
                temperature = 25,
                weatherIcons = listOf("Sunny.png"),
                weatherDescriptions = listOf("it is sunny"),
                weatherCode = 12,
                windDirection = "NW",
                visibility = 16,
                pressure = 7,
                humidity = 3,
                windDegree = 17,
                feelsLike = 15,
                uvIndex = 5,
                windSpeed = 9
            )
        )

        coEvery { weatherRepository.getStateWeather(any()) } returns expectedWeather

        val result = getStateWeatherUseCase.invoke(countryCode)
        val expectedResult = ResultState.Success(expectedWeather)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getCountryStates should return Error with message when repository throws exception with message`() = runTest {
        val countryCode = "EG"

        coEvery { weatherRepository.getStateWeather(any()) } throws Exception("Api Error")

        val result = getStateWeatherUseCase.invoke(countryCode)
        val expectedResult = ResultState.Error("Api Error")

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getCountryStates should return Error with message when repository throws exception without message`() = runTest {
        val countryCode = "EG"

        coEvery { weatherRepository.getStateWeather(any()) } throws Exception()

        val result = getStateWeatherUseCase.invoke(countryCode)
        val expectedResult = ResultState.Error("An error occurred")

        assertEquals(expectedResult, result)
    }
}