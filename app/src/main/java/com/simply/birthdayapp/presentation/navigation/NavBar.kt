package com.simply.birthdayapp.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun NavBar(
    items: List<NavBarItem> = emptyList(),
    navController: NavController,
    onItemClick: (NavBarItem) -> Unit = {},
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar(
        containerColor = AppTheme.colors.lightPink,
    ) {
        items.forEach { navBarItem ->
            val selected = currentBackStackEntry?.destination?.route == navBarItem.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(navBarItem) },
                icon = {
                    Icon(
                        painter = navBarItem.icon,
                        contentDescription = navBarItem.name,
                        tint = if (selected) AppTheme.colors.darkPink else AppTheme.colors.white,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun NavBarPreview() {
    NavBar(navController = rememberNavController())
}