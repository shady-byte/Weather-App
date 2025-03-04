package com.example.weatherapp.data

import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.model.LocationInfo
import com.example.weatherapp.data.model.StateWeather
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.WeatherRepository
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

class WeatherRepositoryUnitTest {
    private val weatherApiService: WeatherApiService = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var weatherRepository: WeatherRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        weatherRepository = WeatherRepositoryImpl(weatherApiService, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getStateWeather should return StateWeather`() = runTest {
        val state = "Alexandria"
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

        coEvery { weatherApiService.getStateWeather(any(), any()) } returns expectedWeather

        val result = weatherRepository.getStateWeather(state)

        assertEquals(expectedWeather, result)
    }

    @Test(expected = Exception::class)
    fun `getStateWeather should throws exception when Api fails`() = runTest {
        val state = "Alexandria"

        coEvery { weatherApiService.getStateWeather(any(), any()) } throws Exception("APi Error")

        weatherRepository.getStateWeather(state)
    }
}