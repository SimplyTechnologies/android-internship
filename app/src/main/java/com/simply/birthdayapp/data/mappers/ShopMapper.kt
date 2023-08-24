package com.simply.birthdayapp.data.mappers

import android.util.Base64
import com.simply.birthdayapp.ShopsQuery
import com.simply.birthdayapp.presentation.models.Shop

fun ShopsQuery.Shop.toShop(): Shop {
    var imageData = byteArrayOf()
    try {
        imageData = Base64.decode(image.substringAfter("base64,"), Base64.DEFAULT)
    } catch (cause: IllegalArgumentException) {
        cause.printStackTrace()
    }
    return Shop(
        id = id,
        name = name,
        image = imageData,
        isFavourite = isFavorite ?: false,
    )
}