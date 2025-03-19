package com.example.weatherapp.presentation

import app.cash.turbine.test
import com.example.weatherapp.data.remote.dto.CountryState
import com.example.weatherapp.domain.usecase.GetCountryStatesUseCase
import com.example.weatherapp.domain.util.ResultState
import com.example.weatherapp.ui.viewModel.StatesViewModel
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

class StatesViewModelUnitTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val getCountryStatesUseCase: GetCountryStatesUseCase = mockk()

    private lateinit var statesViewModel: StatesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        statesViewModel = StatesViewModel(getCountryStatesUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCountryStates emits Success when useCase returns data successfully`() =
        runTest {
            val countryCode = "EG"
            val statesList = mockk<List<CountryState>>()

            coEvery { getCountryStatesUseCase(any()) } returns ResultState.Success(statesList)

            statesViewModel.uiState.test {
                assertEquals(ResultState.Idle, awaitItem())
                statesViewModel.getCountryStates(countryCode)

                assertEquals(ResultState.Success(statesList), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getCountryStates emits Error when useCase returns error`() = runTest {
        val countryCode = "EG"

        coEvery { getCountryStatesUseCase(any()) } returns ResultState.Error("Something went wrong")

        statesViewModel.uiState.test {
            assertEquals(ResultState.Idle, awaitItem())
            statesViewModel.getCountryStates(countryCode)

            assertEquals(ResultState.Error("Something went wrong"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}