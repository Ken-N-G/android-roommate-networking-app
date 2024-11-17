package com.example.roomiesapplication.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CustomHeader(
    headerText: String,
    headerColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = headerText,
        modifier = modifier.fillMaxWidth(),
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = headerColor
    )
}

@Composable
fun CustomBody(
    bodyText: String,
    bodyColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = bodyText,
        modifier = modifier.fillMaxWidth(),
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = bodyColor
    )
}