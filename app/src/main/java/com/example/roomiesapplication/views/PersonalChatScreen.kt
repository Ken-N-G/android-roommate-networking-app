package com.example.roomiesapplication.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.PersonalChatScreenViewModel
import com.example.roomiesapplication.viewmodels.viewmodelfactory.PersonalChatViewModelFactory
import com.example.roomiesapplication.widgets.CustomBackNavigationIconButton
import com.example.roomiesapplication.widgets.CustomRefreshButton
import com.example.roomiesapplication.widgets.CustomTextFieldWithAction
import com.example.roomiesapplication.widgets.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalChatScreen(
    modifier: Modifier = Modifier,
    uid: String,
    viewModel: PersonalChatScreenViewModel = viewModel(factory = PersonalChatViewModelFactory(uid = uid)),
    navController: NavController
) {

    val stateData = viewModel.personalChatState.value

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stateData.OtherUsername,
                actions = {
                    CustomRefreshButton(
                        onClick = {
                            viewModel.OnRefresh()
                        }
                    )
                },
                navigationIcon = {
                    CustomBackNavigationIconButton {
                        navController.popBackStack()
                    }
                }
            )
        },
        bottomBar = {
            CustomTextFieldWithAction(
                value = stateData.MessageContent,
                onValueChange = {
                    viewModel.OnPostContentFieldChange(it)
                },
                action = {
                    viewModel.SendMessage()
                },
                placeHolderText = "Type your message here",
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(contentPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                itemsIndexed(stateData.MessageList) { index, string ->
                    ChatMessage(
                        sentByMe = stateData.SelfUID == stateData.MessageList[index].sender,
                        messageContent = stateData.MessageList[index].content,
                        dateSent = viewModel.formatDate(stateData.MessageList[index].dateSent)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessage(
    modifier: Modifier = Modifier,
    sentByMe: Boolean,
    dateSent: String = "",
    messageContent: String = ""
) {
    when(sentByMe) {
        true -> MyChatMessage(
            modifier = modifier,
            dateSent = dateSent,
            messageContent = messageContent
        )
        false -> ReceivedChatMessage(
        modifier = modifier,
        dateSent = dateSent,
        messageContent = messageContent
    )
    }
}

@Composable
private fun MyChatMessage(
    modifier: Modifier = Modifier,
    dateSent: String = "",
    messageContent: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium)
            .padding(top = MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.End
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                dateSent,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(end = MaterialTheme.spacing.small)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 4.dp,
                    focusedElevation = 4.dp,
                    hoveredElevation = 6.dp,
                    disabledElevation = 0.dp
                ),
                modifier = Modifier.width(250.dp)
            ) {
                Text(
                    messageContent,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(all = MaterialTheme.spacing.small)
                )
            }
        }
    }
}

@Composable
private fun ReceivedChatMessage(
    modifier: Modifier = Modifier,
    dateSent: String = "",
    messageContent: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium)
            .padding(top = MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 4.dp,
                    focusedElevation = 4.dp,
                    hoveredElevation = 6.dp,
                    disabledElevation = 0.dp
                ),
                modifier = Modifier
                    .width(250.dp)
                    .padding(end = MaterialTheme.spacing.small)
            ) {
                Text(
                    messageContent,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(all = MaterialTheme.spacing.small)
                )
            }
            Text(
                dateSent,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(end = MaterialTheme.spacing.small)
            )
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun PersonalChatScreenPreview() {
    PersonalChatScreen()
}

@Preview(showBackground = true)
@Composable
fun ChatMessagePreview() {
    ChatMessage(
        sentByMe = false,
        dateSent = "19/20/2023",
        messageContent = "Hey man want to grab some dinner? like fr fr no cpa on god seriously dude let's grab something to eat"
    )
}

 */