package com.example.stateweather.presentation.weather

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
import com.example.core_module.presentation.component.ShowCircularProgress
import com.example.core_module.presentation.component.ShowMessageBox
import com.example.core_module.presentation.component.TitleTopAppBar
import com.example.core_module.presentation.navigation.WeatherNavigator
import com.example.core_module.presentation.theme.Dimension
import com.example.core_module.presentation.theme.Gainsboro
import com.example.core_module.presentation.theme.WhiteSmoke
import com.example.stateweather.R
import com.example.stateweather.presentation.component.WeatherDetailsRow
import com.example.stateweather.presentation.component.WeatherIcon
import com.example.stateweather.presentation.mapper.getWeatherDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun StateWeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = koinViewModel(),
    navigator: WeatherNavigator,
    countryState: String
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.getStateWeather(countryState)

    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.weather_screen_title, countryState),
                onBackButtonClick = { navigator.popBackStack() })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteSmoke)
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is WeatherUiState.Loading -> {
                    ShowCircularProgress(modifier = modifier)
                }

                is WeatherUiState.Error -> {
                    ShowMessageBox(
                        modifier = modifier,
                        message = stringResource(R.string.no_weather_error)
                    )
                }

                is WeatherUiState.Success -> {
                    WeatherSuccessScreen(stateWeather = state.data)
                }
            }
        }
    }
}

@Composable
fun WeatherSuccessScreen(
    modifier: Modifier = Modifier,
    stateWeather: com.example.stateweather.domain.model.StateWeather
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
fun WeatherCard(
    modifier: Modifier = Modifier,
    stateWeather: com.example.stateweather.domain.model.StateWeather
) {
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
                imageUrl = stateWeather.currentWeather.weatherIcon
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