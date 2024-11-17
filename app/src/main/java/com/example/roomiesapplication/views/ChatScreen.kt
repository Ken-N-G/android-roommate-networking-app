package com.example.roomiesapplication.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomiesapplication.R
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.ChatScreenViewModel
import com.example.roomiesapplication.widgets.CustomBottomNavigationBar
import com.example.roomiesapplication.widgets.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatScreenViewModel = viewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val stateData = viewModel.chatState.value

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Roomies"
            )
        },
        bottomBar = {
            CustomBottomNavigationBar(navController = navController)
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = MaterialTheme.spacing.medium)
            ) {
                itemsIndexed(stateData.ChatList) { index, string ->
                    ChatItem(
                        username = stateData.ChatList[index].username,
                        lastMessageSent = stateData.ChatList[index].lastMessageSent,
                        dateOfLastMessage = viewModel.formatDate(stateData.ChatList[index].dateLastMessageSent),
                        context = context,
                        imageURL = stateData.ChatList[index].profileURL,
                        onClick = {
                            navController.navigate("PersonalChat/" + stateData.ChatList[index].uid)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatItem(
    modifier: Modifier = Modifier,
    username: String = "",
    lastMessageSent: String = "",
    dateOfLastMessage: String = "",
    context: android.content.Context,
    imageURL: String,
    onClick: () -> Unit
) {
    Card(
        onClick = {
            onClick()
        },
        modifier = modifier
            .padding(horizontal = MaterialTheme.spacing.medium)
            .padding(bottom = MaterialTheme.spacing.medium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
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
                )
                Column() {
                    Row(modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)) {
                        Text(
                            username,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            dateOfLastMessage,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.End,
                        )
                    }
                    Text(
                        lastMessageSent,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Divider(modifier = Modifier.fillMaxWidth())
        }
    }
}