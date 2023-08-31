package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.auth.AuthMainScreen
import com.simply.birthdayapp.presentation.ui.screens.main.MainScreen

sealed class RootDestination(val route: String) {
    data object MainScreen : RootDestination("main-screen")
    data object AuthMainScreen : RootDestination("auth-main-screen")
}

@Composable
fun RootNavigation() {
    val rootNavController = rememberNavController()

    fun navigateToMainScreen() {
        rootNavController.navigate(RootDestination.MainScreen.route) {
            popUpTo(RootDestination.AuthMainScreen.route) {
                inclusive = true
            }
        }
    }

    NavHost(
        navController = rootNavController,
        startDestination = RootDestination.AuthMainScreen.route,
    ) {
        composable(RootDestination.MainScreen.route) {
            MainScreen()
        }
        composable(RootDestination.AuthMainScreen.route) {
            AuthMainScreen(
                navigateToMainScreen = { navigateToMainScreen() }
            )
        }
    }
}