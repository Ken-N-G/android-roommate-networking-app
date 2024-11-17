package com.example.roomiesapplication.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomiesapplication.R
import com.example.roomiesapplication.ui.theme.spacing

@Composable
fun CustomProfileHeaderBar(
    fullName: String,
    username: String,
    profileDescription: String,
    context: android.content.Context,
    imageURL: String,
    modifier: Modifier = Modifier,
    onClickProfilePicture: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(imageURL)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.profile_description),
            placeholder = painterResource(id = R.drawable.person_filled),
            error = painterResource(id = R.drawable.person_filled),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)
                .clickable {
                    onClickProfilePicture()
                }
        )
        Column(modifier = Modifier.padding(start = MaterialTheme.spacing.small)) {
            Text(
                fullName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )
            Text(
                username,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )
            Text(
                profileDescription,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall)
            )
        }
    }
}