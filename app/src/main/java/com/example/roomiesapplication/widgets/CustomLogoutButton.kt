package com.example.roomiesapplication.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.roomiesapplication.R

@Composable
fun CustomLogoutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
            .size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logout),
            contentDescription = stringResource(id = R.string.refresh_description),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxSize()
        )
    }
}