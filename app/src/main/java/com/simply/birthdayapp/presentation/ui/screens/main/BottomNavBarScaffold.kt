package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.navigation.BottomDestination
import com.simply.birthdayapp.presentation.navigation.NavBar
import com.simply.birthdayapp.presentation.navigation.NavBarItem

@Composable
fun BottomNavBarScaffold(
    navController: NavController,
    content: @Composable (BoxScope.() -> Unit),
) {
    Scaffold(
        bottomBar = {
            NavBar(
                items = listOf(
                    NavBarItem(
                        name = stringResource(R.string.navbar_item_home),
                        route = BottomDestination.HomeMainScreen.route,
                        icon = painterResource(id = R.drawable.ic_home),
                    ),
                    NavBarItem(
                        name = stringResource(R.string.navbar_item_shops),
                        route = BottomDestination.ShopsMainScreen.route,
                        icon = painterResource(id = R.drawable.ic_shops),
                    ),
                    NavBarItem(
                        name = stringResource(R.string.navbar_item_profile),
                        route = BottomDestination.ProfileMainScreen.route,
                        icon = painterResource(id = R.drawable.ic_profile),
                    ),
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
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
                .padding(it)
                .fillMaxSize(),
            content = content,
        )
    }
}