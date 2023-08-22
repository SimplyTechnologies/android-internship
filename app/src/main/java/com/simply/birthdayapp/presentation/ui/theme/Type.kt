package com.simply.birthdayapp.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class AppTypography(
    val bold: TextStyle,
    val medium: TextStyle,
)

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        bold = TextStyle.Default,
        medium = TextStyle.Default,
    )
}