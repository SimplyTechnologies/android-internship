package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsScreen

@Composable
fun ShopsNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShopsDestination.ShopsScreen.route,
    ) {
        composable(ShopsDestination.ShopsScreen.route) { ShopsScreen() }
    }
}

sealed class ShopsDestination(val route: String) {
    data object ShopsScreen : ShopsDestination("shops-screen")
}