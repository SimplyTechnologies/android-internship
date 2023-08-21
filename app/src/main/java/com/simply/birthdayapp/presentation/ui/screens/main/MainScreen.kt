package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class BottomBarDestination(val route: String) {
    data object HomeMainScreen : BottomBarDestination("home-main-screen")
    data object ShopsMainScreen : BottomBarDestination("shops-main-screen")
    data object ProfileMainScreen : BottomBarDestination("profile-main-screen")
}

@Composable
fun MainScreen() {
    val bottomBarNavController = rememberNavController()

    BottomNavBarScaffold(bottomBarNavController = bottomBarNavController) {
        NavHost(
            navController = bottomBarNavController,
            startDestination = BottomBarDestination.HomeMainScreen.route,
        ) {
            composable(BottomBarDestination.HomeMainScreen.route) { }

            composable(BottomBarDestination.ShopsMainScreen.route) { }

            composable(BottomBarDestination.ProfileMainScreen.route) { }
        }
    }
}