package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = BottomDestination.Home.route,
    ) {
        composable(BottomDestination.Home.route) { }

        composable(BottomDestination.Shops.route) { }

        composable(BottomDestination.Profile.route) { }
    }
}

sealed class BottomDestination(val route: String) {
    object Home : BottomDestination("home")
    object Shops : BottomDestination("shops")
    object Profile : BottomDestination("profile")
}
