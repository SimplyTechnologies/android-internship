package com.simply.birthdayapp.presentation.ui.screens.main.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.main.home.addBirthday.AddBirthdayScreen

sealed class HomeDestination(val route: String) {
    data object HomeScreen : HomeDestination("home-screen")
    data object AddBirthdayScreen : HomeDestination("add-birthday-screen")
    data object BirthdayDetailsScreen : HomeDestination("birthday-details-screen")
}

@Composable
fun HomeMainScreen() {
    val homeNavController = rememberNavController()

    NavHost(navController = homeNavController, startDestination = HomeDestination.HomeScreen.route) {
        composable(HomeDestination.HomeScreen.route) {}
        composable(HomeDestination.AddBirthdayScreen.route) {
            AddBirthdayScreen(
                onDoneClick = { homeNavController.navigate(HomeDestination.HomeScreen.route) },
                onBackClick = { homeNavController.navigate(HomeDestination.HomeScreen.route) },
            )
        }
        composable(HomeDestination.BirthdayDetailsScreen.route) {}
    }
}