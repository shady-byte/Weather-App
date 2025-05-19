package com.example.weatherapp.data
//
//import com.example.weatherapp.data.remote.dto.StateWeatherDto
//import com.example.weatherapp.data.remote.WeatherApiService
//import com.example.weatherapp.data.repository.WeatherRepositoryImpl
//import com.example.weatherapp.domain.repository.WeatherRepository
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
//class WeatherRepositoryUnitTest {
//    private val weatherApiService: WeatherApiService = mockk()
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    private val testDispatcher = UnconfinedTestDispatcher()
//
//    private lateinit var weatherRepository: WeatherRepository
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(testDispatcher)
//        weatherRepository = WeatherRepositoryImpl(weatherApiService, testDispatcher)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `getStateWeather Return State Weather Successfully`() = runTest {
//        val state = "Alexandria"
//        val expectedWeather = mockk<StateWeatherDto>()
//
//        coEvery { weatherApiService.getStateWeather(any()) } returns expectedWeather
//
//        val result = weatherRepository.getStateWeather(state)
//
//        assertEquals(expectedWeather, result)
//    }
//
//    @Test(expected = Exception::class)
//    fun `getStateWeather Throws Exception When Api Fails`() = runTest {
//        val state = "Alexandria"
//
//        coEvery { weatherApiService.getStateWeather(any()) } throws Exception("APi Error")
//
//        weatherRepository.getStateWeather(state)
//    }
//}