package com.simply.birthdayapp.data.mappers

import android.util.Base64
import com.simply.birthdayapp.ShopsQuery
import com.simply.birthdayapp.presentation.models.Shop

fun ShopsQuery.Shop.toShop(): Shop = Shop(
    id = id,
    name = name,
    image = try {
        Base64.decode(image.substringAfter("base64,"), Base64.DEFAULT)
    } catch (cause: IllegalArgumentException) {
        cause.printStackTrace()
        byteArrayOf()
    },
    isFavourite = isFavorite ?: false,
    formattedPhoneNumber = phone?.chunked(3)?.joinToString(" - "),
    address = address,
    addressQuery = address.replace(' ', '+'),
    website = url,
)