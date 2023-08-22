package com.simply.birthdayapp.presentation.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val backgroundPink: Color = Color.Unspecified,
    val lightPink: Color = Color.Unspecified,
    val darkPink: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,
    val black: Color = Color.Unspecified,
    val gray: Color = Color.Unspecified,
)

val LocalAppColors = staticCompositionLocalOf { AppColors() }

val LocalAppTextSelectionColors = staticCompositionLocalOf {
    TextSelectionColors(
        handleColor = Color.Unspecified,
        backgroundColor = Color.Unspecified,
    )
}