package com.example.weatherapp.data

import com.example.weatherapp.data.remote.dto.CountryStateDto
import com.example.weatherapp.data.remote.StatesApiService
import com.example.weatherapp.data.repository.StatesRepositoryImpl
import com.example.weatherapp.domain.repository.StatesRepository
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

class StatesRepositoryUnitTest {
    private val statesApiService: StatesApiService = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var statesRepository: StatesRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        statesRepository = StatesRepositoryImpl(statesApiService, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCountryStatesReturnsListOfStates() = runTest {
        val countryCode = "EG"
        val expectedStates = mockk<List<CountryStateDto>>()

        coEvery { statesApiService.getCountryStates(any()) } returns expectedStates

        val result = statesRepository.getCountryStates(countryCode)

        assertEquals(expectedStates, result)
    }

    @Test
    fun getCountryStatesReturnsEmptyListOfStatesWhenCountryCodeIsIsoNotIso2() =
        runTest {
            val countryCode = "E"
            val expectedStates = emptyList<CountryStateDto>()

            coEvery { statesApiService.getCountryStates(any()) } returns expectedStates

            val result = statesRepository.getCountryStates(countryCode)

            assertEquals(expectedStates, result)
        }

    @Test(expected = Exception::class)
    fun getCountryStatesReturnThrowsExceptionWhenApiFails() = runTest {
        val countryCode = "E"

        coEvery { statesApiService.getCountryStates(any()) } throws  Exception("Api Error")

        statesRepository.getCountryStates(countryCode)
    }
}