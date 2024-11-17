package com.example.roomiesapplication.views

import  androidx.compose.material3.Icon
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.HomeScreenViewModel
import com.example.roomiesapplication.widgets.CustomSmallElevatedButton
import com.example.roomiesapplication.widgets.CustomTopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomiesapplication.R
import com.example.roomiesapplication.navigation.ScreenRoutes
import com.example.roomiesapplication.widgets.CustomBottomNavigationBar
import com.example.roomiesapplication.widgets.CustomRefreshButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(),
    navController: NavController
) {

    val stateData = viewModel.homeState.value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Roomies",
                actions = {
                    CustomRefreshButton(
                        onClick = {
                            viewModel.onRefresh()
                        }
                    )
                }
            )
        },
        bottomBar = {
            CustomBottomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ScreenRoutes.AddPost.route) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.fab_add_description),
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
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
                itemsIndexed(stateData.FeedList) { index, string ->
                    HomePostItem(
                        username = stateData.FeedList[index].username,
                        datePosted = viewModel.formatDate(stateData.FeedList[index].datePosted),
                        postContentText = stateData.FeedList[index].postContent,
                        context = context,
                        imageURL = stateData.FeedList[index].profileURL,
                        selfReferencingUID = viewModel.isUIDSelfReferencing(stateData.FeedList[index].uid),
                        onClickMessageButton = {
                            val isSelfReferencingUID = viewModel.isUIDSelfReferencing(stateData.FeedList[index].uid)
                            if (isSelfReferencingUID) {
                                navController.navigate(ScreenRoutes.Profile.route)
                            } else {
                                navController.navigate("PersonalChat/" + stateData.FeedList[index].uid)
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun HomePostItem(
    modifier: Modifier = Modifier,
    username: String = "",
    datePosted: String = "",
    postContentText: String = "",
    onClickMessageButton: () -> Unit = {},
    context: android.content.Context,
    imageURL: String,
    selfReferencingUID: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = MaterialTheme.spacing.medium)
    ) {
        HomePostItemHead(
            username = username,
            datePosted = datePosted,
            onClick = onClickMessageButton,
            context = context,
            imageURL = imageURL,
            selfReferencingUID = selfReferencingUID
        )
        HomePostItemBody(
            postContentText = postContentText,
            modifier = Modifier.padding(
                start = 64.dp,
                bottom = MaterialTheme.spacing.medium
            )
        )
        Divider(
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HomePostItemHead(
    username: String = "",
    datePosted: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    context: android.content.Context,
    imageURL: String,
    selfReferencingUID: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(bottom = MaterialTheme.spacing.small)
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
            Text(
                username,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                //color = MaterialTheme.colorScheme.onPrimary,
                color = Color.Black
            )
            Text(
                datePosted,
                fontSize = 14.sp,
                //color = MaterialTheme.colorScheme.outline,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomSmallElevatedButton(
            text = when(selfReferencingUID) {
                true -> "Your profile"
                false -> "Message"
            },
            onClick = onClick
        )
    }
}

@Composable
private fun HomePostItemBody(
    postContentText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            postContentText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 8,
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun HomePostItemPreview() {
    HomePostItem()
}

 */