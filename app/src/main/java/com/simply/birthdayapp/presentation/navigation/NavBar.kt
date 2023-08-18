package com.simply.birthdayapp.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class NavBarItem(
    val name: String,
    val route: String,
    val icon: Painter,
)

@Composable
fun NavBar(
    items: List<NavBarItem>,
    navController: NavController,
    onItemClick: (NavBarItem) -> Unit = {},
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar {
        items.forEach {
            val selected = it.route == currentBackStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(it) },
                icon = {
                    Icon(
                        painter = it.icon,
                        contentDescription = it.name,
                        tint = if (selected) Color.Black else Color.Unspecified
                    )
                },
            )
        }
    }
}