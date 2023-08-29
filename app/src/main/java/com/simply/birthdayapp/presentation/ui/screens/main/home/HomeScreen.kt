package com.simply.birthdayapp.presentation.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.BirthdayCard
import com.simply.birthdayapp.presentation.ui.components.LogoTopBar
import com.simply.birthdayapp.presentation.ui.screens.main.LocalSnackbarHostState
import com.simply.birthdayapp.presentation.ui.screens.main.home.birthday.BirthdayViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.home.details.BirthdayDetailsViewModel
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    birthdayViewModel: BirthdayViewModel,
    birthdayDetailsViewModel: BirthdayDetailsViewModel,
    navigateToBirthdayScreen: () -> Unit = {},
    navigateToBirthdayDetailsScreen: () -> Unit = {},
) {
    val birthdayList by homeViewModel.birthdayList.collectAsState()
    val scrollPosition by homeViewModel.scrollPosition.collectAsState()
    val errorState by homeViewModel.errorState.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    val birthdaysLazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val pullRefreshState: PullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { homeViewModel.fetchBirthdays() },
    )

    DisposableEffect(Unit) {
        onDispose { homeViewModel.setScrollPosition(birthdaysLazyListState.firstVisibleItemIndex) }
    }
    LaunchedEffect(errorState) {
        if (errorState) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.failed_birthdays_loading),
                duration = SnackbarDuration.Short,
            )
            homeViewModel.setErrorStateFalse()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.backgroundPink),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoTopBar()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    state = birthdaysLazyListState,
                    contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 16.dp),
                ) {
                    when {
                        birthdayList.isNotEmpty() -> items(birthdayList) { birthday ->
                            BirthdayCard(
                                birthday = birthday,
                                onCardClick = {
                                    birthdayDetailsViewModel.setBirthday(birthday = birthday)
                                    navigateToBirthdayDetailsScreen()
                                },
                            )
                        }

                        else -> item { Box(modifier = Modifier.fillMaxSize()) }
                    }
                }
                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    backgroundColor = AppTheme.colors.backgroundPink,
                    contentColor = AppTheme.colors.lightPink,
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            onClick = {
                birthdayViewModel.setBirthday(null)
                navigateToBirthdayScreen()
            },
            shape = AppTheme.shapes.circle,
            containerColor = AppTheme.colors.lightPink,
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add",
            )
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(getViewModel(), getViewModel(), getViewModel())
}