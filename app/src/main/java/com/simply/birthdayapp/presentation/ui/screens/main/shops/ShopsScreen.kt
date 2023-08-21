package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.LogoTopBar
import com.simply.birthdayapp.presentation.ui.components.SearchBarComponent
import com.simply.birthdayapp.presentation.ui.components.ShopCard
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.compose.getViewModel

@OptIn(FlowPreview::class)
@Composable
fun ShopsScreen(
    shopsViewModel: ShopsViewModel,
) {
    val loading by shopsViewModel.loading.collectAsStateWithLifecycle()
    val shops by shopsViewModel.shops.collectAsStateWithLifecycle()
    val scrollPosition by shopsViewModel.scrollPosition.collectAsStateWithLifecycle()
    val searchBarQuery by shopsViewModel.searchBarQuery.collectAsStateWithLifecycle()

    val shopsLazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)

    LaunchedEffect(shopsLazyListState) {
        snapshotFlow { shopsLazyListState.firstVisibleItemIndex }
            .debounce(500L)
            .collectLatest { shopsViewModel.onScrollPositionChange(it) }
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoTopBar()
        SearchBarComponent(
            query = searchBarQuery,
            onQueryChange = shopsViewModel::onSearchBarQueryChange,
            onSearch = { focusManager.clearFocus() },
            trailingIcon = {
                if (searchBarQuery.isNotEmpty()) {
                    IconButton(onClick = { shopsViewModel.onSearchBarQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear),
                            tint = Color.Gray,
                        )
                    }
                } else {
                    IconButton(onClick = { focusManager.clearFocus() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search),
                            tint = Color.Gray,
                        )
                    }
                }
            },
        )
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
                loading -> item { CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary) }

                shops.isEmpty() -> item {
                    Text(
                        text = stringResource(R.string.no_search_results_found),
                        fontFamily = FontFamily(Font(R.font.karma_medium)),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }

                else -> items(shops) { ShopCard(shop = it) }
            }
        }
    }
}

@Preview
@Composable
private fun ShopsScreenPreview() {
    ShopsScreen(shopsViewModel = getViewModel())
}