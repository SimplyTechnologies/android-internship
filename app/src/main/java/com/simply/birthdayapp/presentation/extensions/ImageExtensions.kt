package com.simply.birthdayapp.presentation.extensions

import android.content.Context
import android.net.Uri
import android.util.Base64

fun ByteArray.uriToBase64(): String {
    return Base64.encodeToString(this, Base64.DEFAULT)
}

fun Uri.uriToByteArray(context: Context): ByteArray? {
    return context.contentResolver.openInputStream(this)?.use { inputStream -> inputStream.buffered().readBytes() }
}
