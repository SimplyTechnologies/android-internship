package com.simply.birthdayapp.presentation.ui.screens.main.shops.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.Shop
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun ShopDetailsComponent(shop: Shop) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val phoneNumberAnnotatedString = buildAnnotatedString {
        shop.formattedPhoneNumber?.let {
            withStyle(AppTheme.typography.urlPrefix) { append(stringResource(R.string.shop_phone_number)) }
            pushStringAnnotation(
                tag = stringResource(R.string.url_annotated_string_tag),
                annotation = stringResource(id = R.string.tel_url, shop.formattedPhoneNumber),
            )
            withStyle(AppTheme.typography.url) { append(shop.formattedPhoneNumber) }
            pop()
        } ?: run {
            withStyle(AppTheme.typography.urlPrefix) { append(stringResource(R.string.shop_phone_number_not_specified)) }
        }
    }
    val addressAnnotatedString = buildAnnotatedString {
        withStyle(AppTheme.typography.urlPrefix) { append(stringResource(R.string.shop_address)) }
        pushStringAnnotation(
            tag = stringResource(R.string.url_annotated_string_tag),
            annotation = stringResource(id = R.string.google_maps_search_url, shop.addressQuery),
        )
        withStyle(AppTheme.typography.url) { append(shop.address) }
        pop()
    }
    val websiteAnnotatedString = buildAnnotatedString {
        shop.website?.let {
            pushStringAnnotation(
                tag = stringResource(R.string.url_annotated_string_tag),
                annotation = shop.website,
            )
            withStyle(AppTheme.typography.url) { append(stringResource(R.string.shop_website)) }
            pop()
        } ?: run {
            withStyle(AppTheme.typography.urlPrefix) { append(stringResource(R.string.shop_website_not_specified)) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp, bottom = 25.dp, start = 50.dp, end = 50.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RoundAsyncImage(
            modifier = Modifier.size(100.dp),
            data = shop.image,
            borderWidth = 1.dp,
            borderColor = AppTheme.colors.gray,
            contentDescription = shop.name,
        )
        Text(
            text = shop.name,
            fontSize = 20.sp,
            style = AppTheme.typography.boldKarmaBlack,
            textAlign = TextAlign.Center,
        )
        ClickableText(
            text = phoneNumberAnnotatedString,
            onClick = {
                phoneNumberAnnotatedString
                    .getStringAnnotations(context.getString(R.string.url_annotated_string_tag), it, it)
                    .firstOrNull()?.let { stringAnnotation -> uriHandler.openUri(stringAnnotation.item) }
            },
        )
        ClickableText(
            text = addressAnnotatedString,
            onClick = {
                addressAnnotatedString
                    .getStringAnnotations(context.getString(R.string.url_annotated_string_tag), it, it)
                    .firstOrNull()?.let { stringAnnotation -> uriHandler.openUri(stringAnnotation.item) }
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        ClickableText(
            text = websiteAnnotatedString,
            onClick = {
                websiteAnnotatedString
                    .getStringAnnotations(context.getString(R.string.url_annotated_string_tag), it, it)
                    .firstOrNull()?.let { stringAnnotation -> uriHandler.openUri(stringAnnotation.item) }
            },
        )
    }
}

@Preview
@Composable
private fun ShopDetailsPreview() {
    val shop = Shop(
        id = 0,
        name = "Kitten",
        image = byteArrayOf(),
        isFavourite = false,
        formattedPhoneNumber = "111 - 222 - 333",
        address = "Cat cafe",
        addressQuery = "Cat+cafe",
        website = "",
    )
    ShopDetailsComponent(shop = shop)
}