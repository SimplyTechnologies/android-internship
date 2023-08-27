package com.simply.birthdayapp.presentation.ui.screens.main.shops.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ShopDetailsScreen(
    shopDetailsViewModel: ShopDetailsViewModel,
    navBack: () -> Unit = {},
) {
    val lastClickedShop by shopDetailsViewModel.lastClickedShop.collectAsStateWithLifecycle()
    val shop = lastClickedShop

    BackHandler { navBack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgroundPink),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppBaseTopBar(onBackClick = navBack)
        if (shop == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.could_not_load_shop_details),
                    style = AppTheme.typography.mediumKarmaDarkPink,
                )
            }
        } else {
            ShopDetails(shop = shop)
        }
    }
}

@Preview
@Composable
private fun ShopDetailsScreenPreview() {
    ShopDetailsScreen(shopDetailsViewModel = getViewModel())
}