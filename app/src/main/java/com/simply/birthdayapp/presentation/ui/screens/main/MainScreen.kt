package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.navigation.NavBar
import com.simply.birthdayapp.presentation.navigation.NavBarItem
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsMainScreen
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsViewModel
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
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

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(AppTheme.shapes.circle)
                        .background(AppTheme.colors.lightPink),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = it.visuals.message,
                        color = AppTheme.colors.white,
                        style = AppTheme.typography.medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        },
        bottomBar = {
            NavBar(
                items = listOf(
                    NavBarItem(
                        name = stringResource(R.string.navbar_item_home),
                        route = BottomBarDestination.HomeMainScreen.route,
                        icon = painterResource(id = R.drawable.ic_home),
                    ),
                    NavBarItem(
                        name = stringResource(R.string.navbar_item_shops),
                        route = BottomBarDestination.ShopsMainScreen.route,
                        icon = painterResource(id = R.drawable.ic_shops),
                    ),
                    NavBarItem(
                        name = stringResource(R.string.navbar_item_profile),
                        route = BottomBarDestination.ProfileMainScreen.route,
                        icon = painterResource(id = R.drawable.ic_profile),
                    ),
                ),
                navController = bottomBarNavController,
                onItemClick = {
                    bottomBarNavController.navigate(it.route) {
                        popUpTo(bottomBarNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
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
}