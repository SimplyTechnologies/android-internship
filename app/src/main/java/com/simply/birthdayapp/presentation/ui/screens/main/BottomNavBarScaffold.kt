package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.navigation.NavBar
import com.simply.birthdayapp.presentation.navigation.NavBarItem
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun BottomNavBarScaffold(
    bottomBarNavController: NavController,
    snackbarHostState: SnackbarHostState,
    content: @Composable (BoxScope.() -> Unit),
) {
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
                .padding(it)
                .fillMaxSize(),
            content = content,
        )
    }
}