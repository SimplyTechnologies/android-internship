package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.LogoTopBar
import com.simply.birthdayapp.presentation.ui.components.SearchBarComponent
import com.simply.birthdayapp.presentation.ui.components.ShopCard
import com.simply.birthdayapp.presentation.ui.screens.main.LocalSnackbarHostState
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class, FlowPreview::class)
@Composable
fun ShopsScreen(shopsViewModel: ShopsViewModel) {
    val loading by shopsViewModel.loading.collectAsStateWithLifecycle()
    val shops by shopsViewModel.shops.collectAsStateWithLifecycle()
    val scrollPosition by shopsViewModel.scrollPosition.collectAsStateWithLifecycle()
    val searchBarQuery by shopsViewModel.searchBarQuery.collectAsStateWithLifecycle()
    val numOfShopsLoadingIsFavourite by shopsViewModel.numOfShopsLoadingIsFavourite.collectAsStateWithLifecycle()
    val lastFavouredShopName by shopsViewModel.lastFavouredShopName.collectAsStateWithLifecycle()
    val lastShopsError by shopsViewModel.lastShopsError.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()
    val shopsLazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)
    val pullRefreshState: PullRefreshState = rememberPullRefreshState(
        refreshing = loading,
        onRefresh = {
            if (numOfShopsLoadingIsFavourite == 0) shopsViewModel.onPullRefresh()
            else coroutineScope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.can_not_load_shops_while_updating_favourites),
                    duration = SnackbarDuration.Short,
                )
            }
        },
    )

    LaunchedEffect(shopsLazyListState) {
        snapshotFlow { shopsLazyListState.firstVisibleItemIndex }
            .debounce(500L)
            .collectLatest { shopsViewModel.onScrollPositionChange(it) }
    }

    LaunchedEffect(lastFavouredShopName) {
        val shopName = lastFavouredShopName
        if (shopName != null) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.favoured_shop_with_name_of, shopName),
                duration = SnackbarDuration.Short,
            )
            shopsViewModel.clearLastFavouredShopName()
        }
    }

    LaunchedEffect(lastShopsError) {
        val error = lastShopsError
        if (error != null) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = context.getString(error.messageId),
                duration = SnackbarDuration.Short,
            )
            shopsViewModel.clearLastShopsError()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            shopsViewModel.onScrollPositionChange(shopsLazyListState.firstVisibleItemIndex)
            shopsViewModel.clearLastFavouredShopName()
            shopsViewModel.clearLastShopsError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgroundPink)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoTopBar()
        SearchBarComponent(
            query = searchBarQuery,
            onQueryChange = shopsViewModel::onSearchBarQueryChange,
            onSearch = { focusManager.clearFocus() },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = shopsLazyListState,
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 15.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when {
                    loading -> item { Box(modifier = Modifier.fillMaxSize()) }

                    shops.isEmpty() -> item {
                        Text(
                            text = stringResource(R.string.no_search_results_found),
                            color = AppTheme.colors.darkPink,
                            style = AppTheme.typography.medium,
                        )
                    }

                    else -> items(shops) { shop ->
                        ShopCard(
                            shop = shop,
                            onIsFavouriteChange = shopsViewModel::onShopIsFavouriteChange,
                        )
                    }
                }
            }
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = loading,
                state = pullRefreshState,
                backgroundColor = AppTheme.colors.backgroundPink,
                contentColor = AppTheme.colors.lightPink,
            )
        }
    }
}

@Preview
@Composable
private fun ShopsScreenPreview() {
    ShopsScreen(shopsViewModel = getViewModel())
}