package com.simply.birthdayapp.presentation.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.presentation.ui.components.BirthdayCard
import com.simply.birthdayapp.presentation.ui.components.LogoTopBar
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = getViewModel()) {
    val birthdayList by homeViewModel.birthdayList.collectAsState()
    val scrollPosition by homeViewModel.scrollPosition.collectAsState()
    val birthdaysLazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)

    DisposableEffect(Unit) {
        onDispose { homeViewModel.setScrollPosition(birthdaysLazyListState.firstVisibleItemIndex) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgroundPink),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoTopBar()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = birthdaysLazyListState,
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) {
            items(birthdayList) {
                BirthdayCard(birthday = it)
            }
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen()
}