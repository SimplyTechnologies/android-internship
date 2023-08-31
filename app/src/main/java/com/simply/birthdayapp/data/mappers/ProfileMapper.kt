package com.simply.birthdayapp.data.mappers

import android.util.Base64
import com.simply.birthdayapp.ProfileQuery
import com.simply.birthdayapp.presentation.models.Profile

fun ProfileQuery.Profile.toProfile(): Profile {
    var imageData: ByteArray? = null

    if (image != null) {
        try {
            imageData = Base64.decode(image.substringAfter("base64,"), Base64.DEFAULT)
        } catch (cause: IllegalArgumentException) {
            cause.printStackTrace()
        }
    }
    return Profile(
        id = id,
        firstName = firstName,
        lastName = lastName,
        image = imageData,
        email = email,
    )
}