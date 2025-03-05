package com.example.weatherapp.data

import com.example.weatherapp.data.remote.dto.CountryState
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
    fun `getCountryStates should return list of states`() = runTest {
        val countryCode = "EG"
        val expectedStates = listOf(
            CountryState(
                id = 1,
                name = "Alexandria",
                iso2 = "Alex"
            ),
            CountryState(
                id = 2,
                name = "Cairo",
                iso2 = "Ca"
            ),
            CountryState(
                id = 3,
                name = "Sharm-Elsheikh",
                iso2 = "Sharm"
            )
        )

        coEvery { statesApiService.getCountryStates(any()) } returns expectedStates

        val result = statesRepository.getCountryStates(countryCode)

        assertEquals(expectedStates, result)
    }

    @Test
    fun `getCountryStates should return empty list of states when countryCode is Iso not Iso2`() =
        runTest {
            val countryCode = "E"
            val expectedStates = emptyList<CountryState>()

            coEvery { statesApiService.getCountryStates(any()) } returns expectedStates

            val result = statesRepository.getCountryStates(countryCode)

            assertEquals(expectedStates, result)
        }

    @Test(expected = Exception::class)
    fun `getCountryStates should return throws exception when Api fails`() = runTest {
        val countryCode = "E"

        coEvery { statesApiService.getCountryStates(any()) } throws  Exception("Api Error")

        statesRepository.getCountryStates(countryCode)
    }
}