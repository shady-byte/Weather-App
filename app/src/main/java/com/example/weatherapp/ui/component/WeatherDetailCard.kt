package com.example.weatherapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Gainsboro

@Composable
fun WeatherDetailCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imageSrc: Int
) {
    Card(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Gainsboro),
        shape = RoundedCornerShape(13.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = imageSrc),
                    contentDescription = stringResource(R.string.image_description),
                    modifier = Modifier.size(15.dp)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = title,
                    style = TextStyle(
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}