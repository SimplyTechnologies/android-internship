package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    val shopsScreenUiState by shopsViewModel.shopsScreenUiState.collectAsStateWithLifecycle()
    var shopNameSearchQuery by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        LogoTopBar()
        CleanableSearchBar(
            query = shopNameSearchQuery,
            onQueryChange = {
                shopNameSearchQuery = it
                shopsViewModel.onSearchShopsByName(shopNameSearchQuery)
            },
        )
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
                shopsScreenUiState.loadingShops -> {
                    item {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                    }
                }

                shopsScreenUiState.filteredShops.isEmpty() -> {
                    item {
                        Text(
                            text = stringResource(R.string.no_search_results_found),
                            fontFamily = FontFamily(Font(R.font.karma_light)),
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }

                else -> {
                    items(shopsScreenUiState.filteredShops) { shop ->
                        ShopCard(shop = shop)
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