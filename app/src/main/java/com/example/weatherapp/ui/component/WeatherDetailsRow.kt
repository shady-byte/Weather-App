package com.example.weatherapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.weatherapp.ui.model.WeatherDetail


@Composable
fun WeatherDetailsRow(
    modifier: Modifier = Modifier,
    rowItems: List<WeatherDetail>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        rowItems.forEach { detail ->
            WeatherDetailCard(
                modifier = Modifier.weight(1f),
                title = stringResource(detail.titleRes),
                subtitle = detail.subtitle,
                imageSrc = detail.imageSrc
            )
        }
    }
}
