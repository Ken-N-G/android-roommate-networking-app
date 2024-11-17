package com.example.roomiesapplication.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(all = 0.dp),
        modifier = modifier
    ) {
        Text(
            text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}