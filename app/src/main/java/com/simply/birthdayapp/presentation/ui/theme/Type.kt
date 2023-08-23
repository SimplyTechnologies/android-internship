package com.simply.birthdayapp.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.simply.birthdayapp.R

@Immutable
data class AppTypography(
    val bold: TextStyle = TextStyle(fontFamily = FontFamily(Font(R.font.karma_bold))),
    val medium: TextStyle = TextStyle(fontFamily = FontFamily(Font(R.font.karma_medium))),
)

val LocalAppTypography = staticCompositionLocalOf { AppTypography() }