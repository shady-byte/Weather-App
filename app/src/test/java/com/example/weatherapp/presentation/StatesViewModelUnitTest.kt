package com.example.weatherapp.presentation
//
//import app.cash.turbine.test
//import com.example.weatherapp.R
//import com.example.weatherapp.domain.model.CountryState
//import com.example.weatherapp.domain.usecase.GetCountryStatesUseCase
//import com.example.weatherapp.ui.util.StatesUiState
//import com.example.weatherapp.ui.viewModel.StatesViewModel
//import io.mockk.coEvery
//import io.mockk.mockk
//import junit.framework.TestCase.assertEquals
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//
//class StatesViewModelUnitTest {
//    @OptIn(ExperimentalCoroutinesApi::class)
//    private val testDispatcher = UnconfinedTestDispatcher()
//
//    private val getCountryStatesUseCase: GetCountryStatesUseCase = mockk()
//
//    private lateinit var statesViewModel: StatesViewModel
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(testDispatcher)
//        statesViewModel = StatesViewModel(getCountryStatesUseCase)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `getCountryStates emits Success when useCase returns data successfully`() =
//        runTest {
//            val countryCode = "EG"
//            val statesList = listOf(
//                mockk<CountryState>(),
//                mockk<CountryState>()
//            )
//
//            coEvery { getCountryStatesUseCase(any()) } returns Result.success(statesList)
//
//            statesViewModel.uiState.test {
//                assertEquals(StatesUiState.Idle, awaitItem())
//                statesViewModel.getCountryStates(countryCode)
//
//                assertEquals(StatesUiState.Success(statesList), awaitItem())
//                cancelAndIgnoreRemainingEvents()
//            }
//        }
//
//    @Test
//    fun `getCountryStates emits Error when useCase returns error`() = runTest {
//        val countryCode = "EG"
//
//        coEvery { getCountryStatesUseCase(any()) } returns Result.failure(
//            exception = Exception("")
//        )
//
//        statesViewModel.uiState.test {
//            assertEquals(StatesUiState.Idle, awaitItem())
//            statesViewModel.getCountryStates(countryCode)
//
//            assertEquals(StatesUiState.Error(R.string.no_iso2_error), awaitItem())
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `getCountryStates emits Error when useCase returns Success with empty list`() = runTest {
//        val countryCode = "EG"
//
//        coEvery { getCountryStatesUseCase(any()) } returns Result.success(emptyList())
//
//        statesViewModel.uiState.test {
//            assertEquals(StatesUiState.Idle, awaitItem())
//            statesViewModel.getCountryStates(countryCode)
//
//            assertEquals(StatesUiState.Error(R.string.no_states), awaitItem())
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//}