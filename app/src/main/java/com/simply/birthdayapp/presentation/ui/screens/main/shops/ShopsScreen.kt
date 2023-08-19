package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.CleanableSearchBar
import com.simply.birthdayapp.presentation.ui.components.LogoTopBar
import com.simply.birthdayapp.presentation.ui.components.ShopCard
import com.simply.birthdayapp.presentation.viewmodels.ShopsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ShopsScreen(
    shopsViewModel: ShopsViewModel,
) {
    val loadingAllShops by shopsViewModel.loadingAllShops.collectAsStateWithLifecycle()
    val allShops by shopsViewModel.allShops.collectAsStateWithLifecycle()
    val filteredShops by shopsViewModel.filteredShops.collectAsStateWithLifecycle()
    val searchBarQuery by shopsViewModel.searchBarQuery.collectAsStateWithLifecycle()
    val searchBarActive by shopsViewModel.searchBarActive.collectAsStateWithLifecycle()

    val allShopsLazyListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        LogoTopBar()
        CleanableSearchBar(
            query = searchBarQuery,
            onQueryChange = { shopsViewModel.onSearchBarQueryChange(it) },
            active = searchBarActive,
            onActiveChange = { shopsViewModel.onSearchBarActiveChange(it) },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 15.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when {
                    filteredShops.isEmpty() -> {
                        item {
                            Text(
                                text = stringResource(R.string.no_search_results_found),
                                fontFamily = FontFamily(Font(R.font.karma_light)),
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }

                    else -> {
                        items(filteredShops) { shop ->
                            ShopCard(shop = shop)
                        }
                    }
                }
            }
        }
        if (searchBarActive.not()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = allShopsLazyListState,
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 15.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when {
                    loadingAllShops -> {
                        item {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                        }
                    }

                    allShops.isEmpty() -> {
                        item {
                            Text(
                                text = stringResource(R.string.no_shops_to_show),
                                fontFamily = FontFamily(Font(R.font.karma_light)),
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }

                    else -> {
                        items(allShops) { shop ->
                            ShopCard(shop = shop)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShopsScreenPreview() {
    ShopsScreen(shopsViewModel = getViewModel())
}