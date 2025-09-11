package com.example.countrystates.domain

import com.example.countrystates.data.mapper.mapToCountryState
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

class GetCountryStatesUseCaseUnitTest {
    private val statesRepository: com.example.countrystates.data.repository.StatesRepositoryImpl = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var getCountryStatesUseCase: com.example.countrystates.domain.usecase.GetCountryStatesUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCountryStatesUseCase =
            com.example.countrystates.domain.usecase.GetCountryStatesUseCase(statesRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCountryStates Return Success When Repository Returns Data`() = runTest {
        val countryCode = "EG"
        val countryStateDto = com.example.countrystates.data.remote.dto.CountryStateDto(
            id = 1,
            name = "Alex",
            iso2 = "Al"
        )
        val countryState = countryStateDto.mapToCountryState()
        val repoStatesList = listOf(
            countryStateDto,
            countryStateDto.copy(name = "Cairo"),
        )

        val useCaseStatesList = listOf(
            countryState,
            countryState.copy(name = "Cairo"),
        )

        coEvery { statesRepository.getCountryStates(any()) } returns repoStatesList

        val result = getCountryStatesUseCase(countryCode)
        val expectedResult = Result.success(useCaseStatesList)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getCountryStates Returns Error With Message When Repository Throws Exception With Message`() =
        runTest {
            val countryCode = "EG"

            coEvery { statesRepository.getCountryStates(any()) } throws Exception("Api Error")

            val result = getCountryStatesUseCase(countryCode)

            assertTrue(result.isFailure)
            assertEquals("Api Error", result.exceptionOrNull()?.message)
        }

    @Test
    fun `getCountryStates Returns Error With Message When Repository Throws Exception Without Message`() =
        runTest {
            val countryCode = "EG"

            coEvery { statesRepository.getCountryStates(any()) } throws Exception()

            val result = getCountryStatesUseCase.invoke(countryCode)

            assertTrue(result.isFailure)
            assertEquals(null, result.exceptionOrNull()?.message)
        }
}