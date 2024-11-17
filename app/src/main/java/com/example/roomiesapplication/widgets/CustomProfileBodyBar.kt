package com.example.roomiesapplication.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.roomiesapplication.ui.theme.spacing

@Composable
fun CustomProfileBodyBar(
    acceptingStatus: String,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                acceptingStatus,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomSmallElevatedButton(
                text = buttonText,
                onClick = onClick

            )
        }
    }
}