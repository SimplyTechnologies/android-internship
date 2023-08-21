package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsMainScreen
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun BottomNavigation(
    navController: NavHostController,
    shopsViewModel: ShopsViewModel = getViewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = BottomDestination.HomeMainScreen.route,
    ) {
        composable(BottomDestination.HomeMainScreen.route) { }

        composable(BottomDestination.ShopsMainScreen.route) {
            ShopsMainScreen(shopsViewModel = shopsViewModel)
        }

        composable(BottomDestination.ProfileMainScreen.route) { }
    }
}

sealed class BottomDestination(val route: String) {
    data object HomeMainScreen : BottomDestination("home-main-screen")
    data object ShopsMainScreen : BottomDestination("shops-main-screen")
    data object ProfileMainScreen : BottomDestination("profile-main-screen")
}