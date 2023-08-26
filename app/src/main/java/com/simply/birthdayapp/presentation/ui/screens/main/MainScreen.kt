package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsMainScreen
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsViewModel
import org.koin.androidx.compose.getViewModel

sealed class BottomBarDestination(val route: String) {
    data object HomeMainScreen : BottomBarDestination("home-main-screen")
    data object ShopsMainScreen : BottomBarDestination("shops-main-screen")
    data object ProfileMainScreen : BottomBarDestination("profile-main-screen")
}

@Composable
fun MainScreen(shopsViewModel: ShopsViewModel = getViewModel()) {
    val bottomBarNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    BottomNavBarScaffold(
        bottomBarNavController = bottomBarNavController,
        snackbarHostState = snackbarHostState,
    ) {
        NavHost(
            navController = bottomBarNavController,
            startDestination = BottomBarDestination.HomeMainScreen.route,
        ) {
            composable(BottomBarDestination.HomeMainScreen.route) { }

            composable(BottomBarDestination.ShopsMainScreen.route) {
                ShopsMainScreen(
                    shopsViewModel = shopsViewModel,
                    onShowSnackbar = { message ->
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short,
                        )
                    },
                )
            }

            composable(BottomBarDestination.ProfileMainScreen.route) { }
        }
    }
}