package com.example.stateweather.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.stateweather.R

@Composable
fun WeatherIcon(modifier: Modifier = Modifier, imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(2.dp, Color.LightGray, CircleShape),
        contentScale = ContentScale.Fit,
        error = painterResource(R.drawable.broken_image)
    )
}