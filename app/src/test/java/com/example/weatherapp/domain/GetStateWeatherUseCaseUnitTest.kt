package com.example.weatherapp.domain

import com.example.weatherapp.data.remote.dto.StateWeather
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
        val expectedWeather = mockk<StateWeather>()

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