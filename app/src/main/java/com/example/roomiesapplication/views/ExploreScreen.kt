package com.example.roomiesapplication.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.ExploreScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomiesapplication.R
import com.example.roomiesapplication.widgets.CustomBottomNavigationBar
import com.example.roomiesapplication.widgets.CustomSmallElevatedButton
import com.example.roomiesapplication.widgets.CustomTextFieldWithAction
import com.example.roomiesapplication.widgets.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreScreenViewModel = viewModel(),
    navController: NavController
) {

    val stateData by viewModel.exploreState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Roomies"
            )
        },
        bottomBar = {
            CustomBottomNavigationBar(navController = navController)
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(contentPadding)
                .padding(horizontal = MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.Start
        ) {
            CustomTextFieldWithAction(
                value = stateData.SearchInput,
                onValueChange = {
                    viewModel.OnSearchInputFieldChange(it)
                },
                action = {
                    viewModel.Search()
                },
                placeHolderText = "Who are you looking for?",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_description),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_description),
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )

            val context = LocalContext.current

            when(stateData.SearchResult.username.isNotEmpty()) {
                true -> SearchResultItem(
                    context = context,
                    imageURL = stateData.SearchResult.profileURL,
                    username = stateData.SearchResult.username,
                    onClickCard = {
                        if(stateData.SearchResult.uid.isNotEmpty()){
                            navController.navigate("ViewOtherProfile/" + stateData.SearchResult.uid.trim())
                        }
                    },
                ) {
                    if(stateData.SearchResult.uid.isNotEmpty()){
                        navController.navigate("PersonalChat/" + stateData.SearchResult.uid.trim())
                    }
                }
                false -> Box() {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResultItem(
    modifier: Modifier = Modifier,
    username: String = "",
    profileDescription: String = "",
    context: android.content.Context,
    imageURL: String,
    onClickCard: () -> Unit,
    onClickElevatedButton: () -> Unit
) {
    Card(
        onClick = {
            onClickCard()
        },
        modifier = modifier
            .padding(horizontal = MaterialTheme.spacing.medium)
            .padding(bottom = MaterialTheme.spacing.medium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
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
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CustomSmallElevatedButton(
                        text = "Message",
                        onClick = onClickElevatedButton
                    )
                }
                Text(
                    profileDescription,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    ExploreScreen()
}

 */