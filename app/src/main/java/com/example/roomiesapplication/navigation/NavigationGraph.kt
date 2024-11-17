package com.example.roomiesapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomiesapplication.views.AddPostScreen
import com.example.roomiesapplication.views.ChatScreen
import com.example.roomiesapplication.views.EditProfileScreen
import com.example.roomiesapplication.views.ExploreScreen
import com.example.roomiesapplication.views.HomeScreen
import com.example.roomiesapplication.views.OnboardingScreen
import com.example.roomiesapplication.views.PersonalChatScreen
import com.example.roomiesapplication.views.ProfileScreen
import com.example.roomiesapplication.views.RegistrationScreen
import com.example.roomiesapplication.views.SignInScreen
import com.example.roomiesapplication.views.ViewOtherProfileScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.Onboarding.route
    ) {
        composable(route = ScreenRoutes.Chat.route) {
            ChatScreen(navController = navController)
        }

        composable(route = ScreenRoutes.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        composable(route = ScreenRoutes.Explore.route) {
            ExploreScreen(navController = navController)
        }

        composable(route = ScreenRoutes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = ScreenRoutes.PersonalChat.route,
            arguments = listOf(navArgument("uid") {
                type = NavType.StringType
            }
            )
        ) {
            PersonalChatScreen(
                uid = it.arguments?.getString("uid") ?: "none",
                navController = navController
            )
        }

        composable(route = ScreenRoutes.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = ScreenRoutes.Registration.route) {
            RegistrationScreen(navController = navController)
        }

        composable(route = ScreenRoutes.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(route = ScreenRoutes.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        
        composable(route = ScreenRoutes.AddPost.route) {
            AddPostScreen(navController = navController)
        }

        composable(
            route = ScreenRoutes.ViewOtherProfile.route,
            arguments = listOf(navArgument("uid") {
                type = NavType.StringType
            }
            )
        ) {
            ViewOtherProfileScreen(
                uid = it.arguments?.getString("uid") ?: "None",
                navController = navController
            )
        }
    }
}

/*
Scaffold(
topBar = {
    CustomTopAppBar(
        title = "Roomies",
        actions = {
            IconButton(
                onClick = {
                    /*TODO*/
                }
            ) {
                CustomNotificationWithNumberIcon(numberOfNotifications = "12")
            }
        }
    )
}
) {

}
*/