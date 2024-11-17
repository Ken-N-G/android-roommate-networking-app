package com.example.roomiesapplication.navigation

sealed class ScreenRoutes(val route: String) {
    object Chat: ScreenRoutes("Chat")
    object EditProfile: ScreenRoutes("EditProfile")
    object Explore: ScreenRoutes("Explore")
    object ForgotPassword: ScreenRoutes("ForgotPassword")
    object Home: ScreenRoutes("Home")
    object Onboarding: ScreenRoutes("Onboarding")
    object PersonalChat: ScreenRoutes("PersonalChat/{uid}")
    object Profile: ScreenRoutes("Profile")
    object Registration: ScreenRoutes("Registration")
    object SignIn: ScreenRoutes("SignIn")
    object AddPost: ScreenRoutes("AddPost")
    object ViewOtherProfile: ScreenRoutes("ViewOtherProfile/{uid}")
}
