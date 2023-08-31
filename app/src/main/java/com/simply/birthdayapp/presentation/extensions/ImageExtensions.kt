package com.simply.birthdayapp.presentation.extensions

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64

fun ByteArray.uriToBase64(): String {
    return Base64.encodeToString(this, Base64.DEFAULT)
}

fun ContentResolver.uriToByteArray(uri: Uri): ByteArray? {
    return this.openInputStream(uri)?.use { inputStream -> inputStream.buffered().readBytes() }
}
