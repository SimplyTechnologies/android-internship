package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class BottomDestination(val route: String) {
    data object HomeMainScreen : BottomDestination("home-main-screen")
    data object ShopsMainScreen : BottomDestination("shops-main-screen")
    data object ProfileMainScreen : BottomDestination("profile-main-screen")
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomDestination.HomeMainScreen.route,
    ) {
        composable(BottomDestination.HomeMainScreen.route) { }

        composable(BottomDestination.ShopsMainScreen.route) { }

        composable(BottomDestination.ProfileMainScreen.route) { }
    }
}