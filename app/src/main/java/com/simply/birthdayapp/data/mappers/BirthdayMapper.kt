package com.simply.birthdayapp.data.mappers

import android.util.Base64
import com.simply.birthdayapp.BirthdayQuery
import com.simply.birthdayapp.presentation.models.Birthday
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun BirthdayQuery.Birthday.toBirthday(): Birthday {
    var imageData: ByteArray? = null

    if (image != null) {
        try {
            imageData = Base64.decode(image.substringAfter("base64,"), Base64.DEFAULT)
        } catch (cause: IllegalArgumentException) {
            cause.printStackTrace()
        }
    }

    return Birthday(
        name = this.name,
        image = imageData,
        relation = this.relation,
        date = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ROOT).format(ZonedDateTime.parse(date.toString()))
    )
}