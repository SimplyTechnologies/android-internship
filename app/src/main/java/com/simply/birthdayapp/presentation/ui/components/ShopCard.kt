package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.models.Shop

@Composable
fun ShopCard(shop: Shop) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
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
                borderColor = MaterialTheme.colorScheme.tertiary,
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 15.dp),
                text = shop.name,
                fontFamily = FontFamily(Font(R.font.karma_bold)),
                color = MaterialTheme.colorScheme.tertiary,
            )
            Icon(
                painter = painterResource(R.drawable.ic_favourite),
                contentDescription = stringResource(R.string.favour),
            )
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
        isFavorite = false,
    )
    ShopCard(shop)
}