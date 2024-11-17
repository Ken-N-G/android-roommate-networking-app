package com.example.roomiesapplication.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.ViewOtherProfileScreenViewModel
import com.example.roomiesapplication.viewmodels.viewmodelfactory.ViewOtherProfileViewModelFactory
import com.example.roomiesapplication.widgets.CustomBackNavigationIconButton
import com.example.roomiesapplication.widgets.CustomProfileBodyBar
import com.example.roomiesapplication.widgets.CustomProfileHeaderBar
import com.example.roomiesapplication.widgets.CustomRefreshButton
import com.example.roomiesapplication.widgets.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewOtherProfileScreen(
    modifier: Modifier = Modifier,
    uid: String,
    viewModel: ViewOtherProfileScreenViewModel = viewModel(factory = ViewOtherProfileViewModelFactory(uid = uid)),
    navController: NavController
) {

    val stateData = viewModel.viewOtherProfileScreenState.value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Roomies",
                actions = {
                    CustomRefreshButton(
                        onClick = {
                            viewModel.OnRefresh()
                        },
                        modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                    )
                },
                navigationIcon = {
                    CustomBackNavigationIconButton {
                        navController.popBackStack()
                    }
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
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(top = MaterialTheme.spacing.medium)
        ) {
            CustomProfileHeaderBar(
                fullName = stateData.UserEntryData.fullname,
                username = stateData.UserEntryData.username,
                profileDescription = stateData.UserEntryData.profileDescription,
                context = context,
                onClickProfilePicture = {

                },
                imageURL = stateData.UserEntryData.profileURL
            )
            CustomProfileBodyBar(
                acceptingStatus = when(stateData.UserEntryData.acceptingStatus) {
                    true -> "This user is accepting roommates"
                    false -> "This user is not accepting roommates"
                                                                                },
                buttonText = "Message user",
                onClick = {
                    navController.navigate("PersonalChat/" + stateData.UserEntryData.uid)
                }
            )
        }
    }
1}