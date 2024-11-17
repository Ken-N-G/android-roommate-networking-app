package com.example.roomiesapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBar(
    val route: String,
    val contentDescription: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home: BottomBar("Home", "Home icon for home bottom navigation item", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Explore: BottomBar("Explore", "Explore icon for explore bottom navigation item", "Explore", Icons.Filled.Search, Icons.Outlined.Search)
    object Chat: BottomBar("Chat", "Chat icon for chat bottom navigation item", "Chat", Icons.Filled.Send, Icons.Outlined.Send)
    object Profile: BottomBar("Profile", "Profile icon for profile bottom navigation item", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}
