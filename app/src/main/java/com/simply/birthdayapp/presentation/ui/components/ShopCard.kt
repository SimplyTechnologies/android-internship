package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.Shop
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun ShopCard(
    shop: Shop,
    onIsFavouriteChange: (Shop) -> Unit = {},
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.circle)
            .clickable { onClick() },
        shape = AppTheme.shapes.mediumRoundedCorners,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.white),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundAsyncImage(
                modifier = Modifier.size(70.dp),
                data = shop.image,
                contentDescription = shop.name,
                borderColor = AppTheme.colors.darkPink,
                borderWidth = 1.dp,
                placeholder = painterResource(id = R.drawable.placeholder_shop),
                error = painterResource(id = R.drawable.placeholder_shop),
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 15.dp),
                text = shop.name,
                fontSize = 20.sp,
                style = AppTheme.typography.boldKarmaBlack,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (shop.isLoadingFavourite) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color =
                    if (shop.isFavourite) AppTheme.colors.gray
                    else AppTheme.colors.lightPink,
                )
            } else {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { onIsFavouriteChange(shop) },
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (shop.isFavourite) R.drawable.ic_heart_filled
                            else R.drawable.ic_heart_empty,
                        ),
                        contentDescription = stringResource(
                            id = if (shop.isFavourite) R.string.remove_from_favourites
                            else R.string.add_to_favourites,
                        ),
                        tint = if (shop.isFavourite) AppTheme.colors.lightPink else AppTheme.colors.gray,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShopCardPreview() {
    val shop = Shop(
        id = 0,
        name = "Kitten",
        image = byteArrayOf(),
        isFavourite = false,
        rating = 2.5,
        formattedPhoneNumber = null,
        address = "",
        addressQuery = "",
        website = null,
    )
    ShopCard(shop)
}