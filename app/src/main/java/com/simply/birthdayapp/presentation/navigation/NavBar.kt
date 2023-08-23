package com.simply.birthdayapp.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
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
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        containerColor = AppTheme.colors.lightPink,
    ) {
        items.forEach {
            val selected = it.route == currentBackStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(it) },
                icon = {
                    Icon(
                        painter = it.icon,
                        contentDescription = it.name,
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