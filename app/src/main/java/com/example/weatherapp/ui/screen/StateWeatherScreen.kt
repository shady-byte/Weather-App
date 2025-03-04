package com.example.weatherapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherapp.R
import com.example.weatherapp.data.model.StateWeather
import com.example.weatherapp.domain.util.ResultState
import com.example.weatherapp.ui.component.BackButton
import com.example.weatherapp.ui.component.ShowCircularProgress
import com.example.weatherapp.ui.component.ShowMessageBox
import com.example.weatherapp.ui.component.TitleTopAppBar
import com.example.weatherapp.ui.component.WeatherDetailCard
import com.example.weatherapp.ui.component.WeatherIcon
import com.example.weatherapp.ui.model.WeatherDetail
import com.example.weatherapp.ui.theme.Dimensions
import com.example.weatherapp.ui.theme.Gainsboro
import com.example.weatherapp.ui.theme.WhiteSmoke
import com.example.weatherapp.viewModel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StateWeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = koinViewModel(),
    navController: NavHostController,
    countryState: String
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.getStateWeather(countryState)

    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.weather_screen_title, countryState),
                onBackButtonClick = { navController.navigateUp() })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteSmoke)
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is ResultState.Idle -> {
                    ShowMessageBox(
                        modifier = modifier,
                        message = stringResource(R.string.fetch_weather)
                    )
                }

                is ResultState.Loading -> {
                    ShowCircularProgress(modifier = modifier)
                }

                is ResultState.Error -> {
                    ShowMessageBox(
                        modifier = modifier,
                        message = stringResource(R.string.no_weather_error)
                    )
                }

                is ResultState.Success -> {
                    WeatherSuccessScreen(stateWeather = state.data)
                }
            }
        }
    }
}

@Composable
fun WeatherSuccessScreen(
    modifier: Modifier = Modifier,
    stateWeather: StateWeather
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WeatherCard(stateWeather = stateWeather)

        Spacer(modifier = Modifier.padding(20.dp))
        WeatherDetailsSection(stateWeather = stateWeather)
    }
}

@Composable
fun WeatherCard(modifier: Modifier = Modifier, stateWeather: StateWeather) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(color = Gainsboro, shape = RoundedCornerShape(13.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 60.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WeatherIcon(
                imageUrl = stateWeather.currentWeather.weatherIcons.first()
            )

            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = stateWeather.location.country, style = TextStyle(fontSize = 15.sp)
            )

            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = stringResource(
                    R.string.temperature_degree,
                    stateWeather.currentWeather.temperature
                ),
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = stringResource(
                    R.string.feels_like,
                    stateWeather.currentWeather.feelsLike
                ),
                style = TextStyle(fontSize = 15.sp)
            )

            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = stateWeather.currentWeather.observationTime,
                style = TextStyle(fontSize = 15.sp)
            )
        }
    }
}

@Composable
fun WeatherDetailsSection(
    modifier: Modifier = Modifier,
    stateWeather: StateWeather
) {
    val weatherDetails = listOf(
        WeatherDetail(
            R.string.uv_index,
            stateWeather.currentWeather.uvIndex.toString(),
            R.drawable.uv_index_icon
        ),
        WeatherDetail(
            R.string.humidity,
            stringResource(
                R.string.humidity_degree,
                stateWeather.currentWeather.humidity.toString()
            ),
            R.drawable.humidity_icon
        ),
        WeatherDetail(
            R.string.wind_speed,
            stringResource(
                R.string.wind_speed_degree,
                stateWeather.currentWeather.windSpeed.toString()
            ),
            R.drawable.wind_speed_icon
        ),
        WeatherDetail(
            R.string.visibility,
            stringResource(R.string.visibility_degree, stateWeather.currentWeather.visibility),
            R.drawable.visibility_icon
        ),
        WeatherDetail(
            R.string.pressure,
            stringResource(
                R.string.pressure_degree,
                stateWeather.currentWeather.pressure.toString()
            ),
            R.drawable.pressure_icon
        ),
        WeatherDetail(
            R.string.wind_direction,
            stateWeather.currentWeather.windDirection,
            R.drawable.wind_direction_icon
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.screenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimensions.screenPadding)
    ) {
        items(weatherDetails) { detail ->
            WeatherDetailCard(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(detail.titleRes),
                subtitle = detail.subtitle,
                imageSrc = detail.imageSrc
            )
        }
    }
}