package com.example.weatherapp.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TopAppBar(
        title = {
            SearchOutlinedTextField(
                modifier = modifier,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onSearchClick = onSearchClick,
                onSearchDone = {
                    keyboardController?.hide()
                }
            )
        }
    )
}