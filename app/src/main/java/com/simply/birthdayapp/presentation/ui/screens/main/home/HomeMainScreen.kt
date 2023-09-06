package com.simply.birthdayapp.presentation.ui.screens.main.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.main.home.birthday.BirthdayScreen
import com.simply.birthdayapp.presentation.ui.screens.main.home.birthday.BirthdayViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.home.details.BirthdayDetailsScreen
import com.simply.birthdayapp.presentation.ui.screens.main.home.details.BirthdayDetailsViewModel
import org.koin.androidx.compose.getViewModel

sealed class HomeDestination(val route: String) {
    data object HomeScreen : HomeDestination("home-screen")
    data object BirthdayScreen : HomeDestination("birthday-screen")
    data object BirthdayDetailsScreen : HomeDestination("birthday-details-screen")
}

@Composable
fun HomeMainScreen(
    onNavigateToShops: () -> Unit,
    homeViewModel: HomeViewModel = getViewModel(),
    birthdayViewModel: BirthdayViewModel = getViewModel(),
    birthdayDetailsViewModel: BirthdayDetailsViewModel = getViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val homeNavController = rememberNavController()

    NavHost(navController = homeNavController, startDestination = HomeDestination.HomeScreen.route) {
        composable(HomeDestination.HomeScreen.route) {
            HomeScreen(
                homeViewModel = homeViewModel,
                birthdayViewModel = birthdayViewModel,
                birthdayDetailsViewModel = birthdayDetailsViewModel,
                navigateToBirthdayScreen = { homeNavController.navigate(HomeDestination.BirthdayScreen.route) },
                navigateToBirthdayDetailsScreen = { homeNavController.navigate(HomeDestination.BirthdayDetailsScreen.route) },
            )
        }
        composable(HomeDestination.BirthdayScreen.route) {
            BirthdayScreen(
                birthdayViewModel = birthdayViewModel,
                homeViewModel = homeViewModel,
                navigateToHomeScreen = {
                    focusManager.clearFocus()
                    homeNavController.navigate(HomeDestination.HomeScreen.route) {
                        popUpTo(HomeDestination.HomeScreen.route)
                    }
                },
                onBackClick = {
                    focusManager.clearFocus()
                    homeNavController.navigateUp()
                },
            )
        }
        composable(HomeDestination.BirthdayDetailsScreen.route) {
            BirthdayDetailsScreen(
                birthdayViewModel = birthdayViewModel,
                birthdayDetailsViewModel = birthdayDetailsViewModel,
                navigateToShopsScreen = onNavigateToShops,
                navigateToBirthdayScreen = { homeNavController.navigate(HomeDestination.BirthdayScreen.route) },
                onBackClick = { homeNavController.navigateUp() },
            )
        }
    }
}