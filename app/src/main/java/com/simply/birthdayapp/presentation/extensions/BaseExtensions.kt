package com.simply.birthdayapp.presentation.extensions

import android.content.Context
import android.net.Uri
import android.util.Base64

fun Uri.uriToBase64(context: Context): String {
    val imageBytes = context.contentResolver.openInputStream(this)?.use { inputStream -> inputStream.buffered().readBytes() }
    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
}