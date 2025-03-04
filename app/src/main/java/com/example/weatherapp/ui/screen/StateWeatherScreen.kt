package com.example.weatherapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherapp.R
import com.example.weatherapp.data.model.StateWeather
import com.example.weatherapp.domain.util.ResultState
import com.example.weatherapp.ui.component.ShowCircularProgress
import com.example.weatherapp.ui.component.ShowMessageBox
import com.example.weatherapp.ui.component.TitleTopAppBar
import com.example.weatherapp.ui.component.WeatherDetailsRow
import com.example.weatherapp.ui.component.WeatherIcon
import com.example.weatherapp.ui.theme.Dimension
import com.example.weatherapp.ui.theme.Gainsboro
import com.example.weatherapp.ui.theme.WhiteSmoke
import com.example.weatherapp.ui.util.WeatherDataHelper.getWeatherDetails
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
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimension.mediumPadding),
        verticalArrangement = Arrangement.spacedBy(Dimension.screenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            WeatherCard(stateWeather = stateWeather)
        }

        item {
            Spacer(modifier = Modifier.padding(Dimension.screenPadding))
        }

        items(stateWeather.getWeatherDetails(context).chunked(2)) { rowItems ->
            WeatherDetailsRow(rowItems = rowItems)
        }
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