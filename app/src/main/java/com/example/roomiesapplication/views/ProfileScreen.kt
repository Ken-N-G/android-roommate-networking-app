package com.example.roomiesapplication.views

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.roomiesapplication.ui.theme.spacing
import com.example.roomiesapplication.viewmodels.ProfileScreenViewModel
import com.example.roomiesapplication.widgets.CustomProfileBodyBar
import com.example.roomiesapplication.widgets.CustomProfileHeaderBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.roomiesapplication.navigation.ScreenRoutes
import com.example.roomiesapplication.widgets.CustomBottomNavigationBar
import com.example.roomiesapplication.widgets.CustomLogoutButton
import com.example.roomiesapplication.widgets.CustomRefreshButton
import com.example.roomiesapplication.widgets.CustomTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = viewModel(),
    navController: NavController
) {

    val stateData by viewModel.profileScreenState.collectAsState()
    val context = LocalContext.current
    val coroutineContext = rememberCoroutineScope()

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel.onURIResult(uri, context)
        }
    )

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Roomies",
                actions = {
                    CustomLogoutButton(
                        onClick = {
                            viewModel.Logout()
                            Toast.makeText(
                                context,
                                "You have been logged out!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(ScreenRoutes.Onboarding.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                            }
                        },
                        modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                    )
                    CustomRefreshButton(
                        onClick = {
                            coroutineContext.launch {
                                viewModel.FetchUserData()
                            }
                        }
                    )
                }
            )
        },
        bottomBar = {
            CustomBottomNavigationBar(navController = navController)
        },
        modifier = modifier
    ) { contentPadding ->
        Column(modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(contentPadding)
            .padding(horizontal = MaterialTheme.spacing.medium)
            .padding(top = MaterialTheme.spacing.medium)
        ) {
            CustomProfileHeaderBar(
                fullName = stateData.Fullname,
                username = stateData.Username,
                profileDescription = stateData.ProfileDescription,
                context = context,
                onClickProfilePicture = {
                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                imageURL = stateData.ProfileURL
            )
            CustomProfileBodyBar(
                acceptingStatus = when(stateData.AcceptingStatus) {
                    true -> "You are accepting roommates"
                    false -> "You are not accepting roommates"
                                                                  },
                buttonText = "Edit profile",
                onClick = {
                    navController.navigate(ScreenRoutes.EditProfile.route)
                }
            )
        }
    }
}