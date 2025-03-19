package com.example.weatherapp.presentation

import app.cash.turbine.test
import com.example.weatherapp.data.remote.dto.StateWeather
import com.example.weatherapp.domain.usecase.GetStateWeatherUseCase
import com.example.weatherapp.domain.util.ResultState
import com.example.weatherapp.ui.viewModel.WeatherViewModel
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

class WeatherViewModelUnitTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val getStateWeatherUseCase: GetStateWeatherUseCase = mockk()

    private lateinit var weatherViewModel: WeatherViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        weatherViewModel = WeatherViewModel(getStateWeatherUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getStateWeather emits Success when useCase returns data successfully`() = runTest {
        val stateName = "Alexandria"
        val expectedWeather = mockk<StateWeather>()

        coEvery { getStateWeatherUseCase(any()) } returns ResultState.Success(expectedWeather)

        weatherViewModel.uiState.test {
            assertEquals(ResultState.Loading, awaitItem())
            weatherViewModel.getStateWeather(stateName)

            assertEquals(ResultState.Success(expectedWeather), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getStateWeather emits Error when useCase returns error with message`() = runTest {
        val stateName = "Alexandria"

        coEvery { getStateWeatherUseCase(any()) } returns ResultState.Error("Something went wrong")

        weatherViewModel.uiState.test {
            assertEquals(ResultState.Loading, awaitItem())
            weatherViewModel.getStateWeather(stateName)

            assertEquals(ResultState.Error("Something went wrong"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}