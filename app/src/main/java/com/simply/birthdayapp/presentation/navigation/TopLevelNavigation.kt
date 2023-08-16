package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.tabs.TabsScreen

@Composable
fun TopLevelNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.Tabs.route,
    ) {
        composable(TopLevelDestination.Tabs.route) {
            TabsScreen()
        }
    }
}

sealed class TopLevelDestination(val route: String) {
    object Tabs : TopLevelDestination("tabs")
}