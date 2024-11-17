package com.example.roomiesapplication.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.roomiesapplication.ui.theme.OffGrey
import com.example.roomiesapplication.ui.theme.spacing

@Composable
fun CustomTextPromptWithButton(
    startText: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    ) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            startText,
            fontSize = 12.sp,
            color = OffGrey,
            modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall)
        )
        CustomTextButton(
            text = buttonText,
            onClick = onClick,
        )
    }
}