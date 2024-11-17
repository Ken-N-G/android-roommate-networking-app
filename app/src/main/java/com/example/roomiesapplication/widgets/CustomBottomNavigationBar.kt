package com.example.roomiesapplication.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.roomiesapplication.navigation.BottomBar

@Composable
fun CustomBottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val screens = listOf(
        BottomBar.Home,
        BottomBar.Explore,
        BottomBar.Chat,
        BottomBar.Profile
    )


    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = backStackEntry?.destination?.route
        screens.forEach { screen ->
            NavigationBarItem(
                selected = screen.route == currentScreen,
                onClick = {
                    navController.navigate(screen.route)
                },
                icon = {
                    when(screen.route == currentScreen) {
                        true -> Icon(
                            imageVector = screen.selectedIcon,
                            contentDescription = screen.contentDescription,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        false -> Icon(
                            imageVector = screen.unselectedIcon,
                            contentDescription = screen.contentDescription,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                label = {
                    when(screen.route == currentScreen) {
                        true -> Text(
                            screen.label,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        )
                        false -> Text(
                            screen.label,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            )
        }
    }
}