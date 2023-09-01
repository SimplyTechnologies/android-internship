package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.main.home.HomeMainScreen
import com.simply.birthdayapp.presentation.ui.screens.main.profile.ProfileMainScreen
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsMainScreen
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsViewModel
import org.koin.androidx.compose.getViewModel

sealed class BottomBarDestination(val route: String) {
    data object HomeMainScreen : BottomBarDestination("home-main-screen")
    data object ShopsMainScreen : BottomBarDestination("shops-main-screen")
    data object ProfileMainScreen : BottomBarDestination("profile-main-screen")
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = getViewModel(),
    shopsViewModel: ShopsViewModel = getViewModel(),
    onSignOutClicked: () -> Unit = {},
) {
    val bottomBarNavController = rememberNavController()
    val bottomNavBarVisible by mainViewModel.bottomNavBarVisible.collectAsStateWithLifecycle()

    BottomNavBarScaffold(
        bottomNavBarVisible = bottomNavBarVisible,
        bottomBarNavController = bottomBarNavController,
    ) {
        NavHost(
            navController = bottomBarNavController,
            startDestination = BottomBarDestination.HomeMainScreen.route,
        ) {
            composable(BottomBarDestination.HomeMainScreen.route) {
                HomeMainScreen(
                    mainViewModel = mainViewModel,
                    onNavigateToShops = {
                        bottomBarNavController.navigate(BottomBarDestination.ShopsMainScreen.route) {
                            popUpTo(bottomBarNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }

            composable(BottomBarDestination.ShopsMainScreen.route) {
                ShopsMainScreen(shopsViewModel = shopsViewModel)
            }

            composable(BottomBarDestination.ProfileMainScreen.route) {
                ProfileMainScreen(
                    mainViewModel = mainViewModel,
                    onSignOutClicked = onSignOutClicked,
                )
            }
        }
    }
}