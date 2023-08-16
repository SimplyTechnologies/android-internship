package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun TabsNavigation(
    tabsNavController: NavHostController,
) {
    NavHost(
        navController = tabsNavController,
        startDestination = TabsDestination.Home.route,
    ) {
        composable(TabsDestination.Home.route) { }

        composable(TabsDestination.Shops.route) { }

        composable(TabsDestination.Profile.route) { }
    }
}

sealed class TabsDestination(val route: String) {
    object Home : TabsDestination("home")
    object Shops : TabsDestination("shops")
    object Profile : TabsDestination("profile")
}