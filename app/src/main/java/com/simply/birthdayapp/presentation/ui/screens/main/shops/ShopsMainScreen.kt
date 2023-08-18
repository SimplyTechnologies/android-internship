package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class ShopsDestination(val route: String) {
    data object ShopsScreen : ShopsDestination("shops-screen")
}

@Composable
fun ShopsMainScreen() {
    val shopsNavController = rememberNavController()

    NavHost(
        navController = shopsNavController,
        startDestination = ShopsDestination.ShopsScreen.route,
    ) {
        composable(ShopsDestination.ShopsScreen.route) { ShopsScreen() }
    }
}