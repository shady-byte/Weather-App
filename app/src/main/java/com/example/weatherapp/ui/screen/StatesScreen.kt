package com.example.weatherapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.CountryState
import com.example.weatherapp.ui.util.StatesUiState
import com.example.weatherapp.ui.component.SearchTopBar
import com.example.weatherapp.ui.component.ShowCircularProgress
import com.example.weatherapp.ui.component.ShowMessageBox
import com.example.weatherapp.ui.component.StateCard
import com.example.weatherapp.ui.navigation.NavigationRoutes
import com.example.weatherapp.ui.theme.Dimension
import com.example.weatherapp.ui.viewModel.StatesViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StatesScreen(
    modifier: Modifier = Modifier,
    viewModel: StatesViewModel = koinViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(topBar = {
        SearchTopBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onSearchClick = { viewModel.getCountryStates(searchQuery) }
        )
    }) { paddingValue ->
        when (val state = uiState) {
            is StatesUiState.Idle -> {
                ShowMessageBox(
                    modifier = modifier.padding(paddingValue),
                    message = stringResource(R.string.enter_Iso2_message)
                )
            }

            is StatesUiState.Loading -> {
                ShowCircularProgress(modifier = modifier.padding(paddingValue))
            }

            is StatesUiState.Error -> {
                ShowMessageBox(
                    modifier = modifier.padding(paddingValue),
                    message = stringResource(state.messageRes)
                )
            }

            is StatesUiState.Success -> {
                StatesSuccessScreen(
                    modifier = modifier.padding(paddingValue),
                    states = state.data,
                    onItemClick = { navController.navigate(NavigationRoutes.weatherRoute(it.name)) }
                )
            }
        }
    }
}

@Composable
fun StatesSuccessScreen(
    modifier: Modifier = Modifier,
    states: List<CountryState>,
    onItemClick: (state: CountryState) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimension.screenPadding),
            contentPadding = PaddingValues(
                top = Dimension.smallPadding,
                bottom = Dimension.smallPadding
            )
        ) {
            item {
                Text(
                    text = stringResource(R.string.country_states),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(states) { state ->
                StateCard(state = state) {
                    onItemClick(state)
                }
            }
        }
    }
}