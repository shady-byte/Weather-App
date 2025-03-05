package com.example.weatherapp.domain

import com.example.weatherapp.data.remote.dto.CountryState
import com.example.weatherapp.domain.repository.StatesRepository
import com.example.weatherapp.domain.usecase.GetCountryStatesUseCase
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

class GetCountryStatesUseCaseUnitTest {
    private val statesRepository: StatesRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var getCountryStatesUseCase: GetCountryStatesUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCountryStatesUseCase = GetCountryStatesUseCase(statesRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should return Success when repository returns data`() = runTest {
        val countryCode = "EG"
        val statesList =
            listOf(
                CountryState(
                    id = 1,
                    name = "Cairo",
                    iso2 = "Ca"
                ),
                CountryState(
                    id = 2,
                    name = "Alexandria",
                    iso2 = "Alex"
                ),
                CountryState(
                    id = 3,
                    name = "Luxor",
                    iso2 = "Lu"
                )
            )

        coEvery { statesRepository.getCountryStates(any()) } returns statesList

        val result = getCountryStatesUseCase.invoke(countryCode)
        val expectedResult = ResultState.Success(statesList)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getCountryStates should return Error with message when repository throws exception with message`() = runTest {
        val countryCode = "EG"

        coEvery { statesRepository.getCountryStates(any()) } throws Exception("Api Error")

        val result = getCountryStatesUseCase.invoke(countryCode)
        val expectedResult = ResultState.Error("Api Error")

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getCountryStates should return Error with message when repository throws exception without message`() = runTest {
        val countryCode = "EG"

        coEvery { statesRepository.getCountryStates(any()) } throws Exception()

        val result = getCountryStatesUseCase.invoke(countryCode)
        val expectedResult = ResultState.Error("An error occurred")

        assertEquals(expectedResult, result)
    }
}