package com.example.weatherapp.domain

import com.example.weatherapp.data.remote.dto.CurrentWeatherDto
import com.example.weatherapp.data.remote.dto.LocationInfoDto
import com.example.weatherapp.data.remote.dto.StateWeatherDto
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GetStateWeatherUseCase
import com.example.weatherapp.domain.util.mapToStateWeather
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
        val repoStateWeatherDto = StateWeatherDto(
            locationInfo = LocationInfoDto(
                name = "Cairo",
                country = "Egypt",
                timezoneId = "GMT+2"
            ),
            currentWeather = CurrentWeatherDto(
                observationTime = "1:00PM",
                temperature = 14,
                weatherCode = 1,
                weatherIcons = listOf("icon1", "icon2"),
                weatherDescriptions = listOf("1", "2"),
                windSpeed = 3,
                windDegree = 8,
                windDirection = "NWN",
                pressure = 1,
                humidity = 9,
                feelsLike = 9,
                uvIndex = 2,
                visibility = 4
            )
        )

        coEvery { weatherRepository.getStateWeather(any()) } returns repoStateWeatherDto

        val result = getStateWeatherUseCase.invoke(countryCode)
        val expectedResult = Result.success(repoStateWeatherDto.mapToStateWeather())

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getCountryStates should return Error with message when repository throws exception with message`() =
        runTest {
            val countryCode = "EG"

            coEvery { weatherRepository.getStateWeather(any()) } throws Exception("Api Error")

            val result = getStateWeatherUseCase.invoke(countryCode)

            assertTrue(result.isFailure)
            assertEquals("Api Error", result.exceptionOrNull()?.message)
        }

    @Test
    fun `getCountryStates should return Error with message when repository throws exception without message`() =
        runTest {
            val countryCode = "EG"

            coEvery { weatherRepository.getStateWeather(any()) } throws Exception()

            val result = getStateWeatherUseCase.invoke(countryCode)

            assertTrue(result.isFailure)
            assertEquals(null, result.exceptionOrNull()?.message)
        }
}