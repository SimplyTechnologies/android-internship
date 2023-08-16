package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.main.MainScreen

@Composable
fun RootNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RootDestination.MainScreen.route,
    ) {
        composable(RootDestination.MainScreen.route) {
            MainScreen()
        }
    }
}

sealed class RootDestination(val route: String) {
    data object MainScreen : RootDestination("main-screen")
}