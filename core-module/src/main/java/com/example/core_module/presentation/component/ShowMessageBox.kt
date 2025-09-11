package com.example.core_module.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.core_module.presentation.theme.Dimension

@Composable
fun ShowMessageBox(modifier: Modifier = Modifier, message: String) {
    Box(
        modifier = modifier.fillMaxSize()
            .padding(horizontal = Dimension.screenPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp)
        )
    }
}