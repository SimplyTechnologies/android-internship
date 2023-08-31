package com.simply.birthdayapp.presentation.extensions

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

fun Context.sendMessage(birthdayMessage: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, birthdayMessage)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    ContextCompat.startActivity(this, shareIntent, null)
}