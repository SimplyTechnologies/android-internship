package com.simply.birthdayapp.presentation.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val backgroundPink: Color = Color(0xFFFFF5F4),
    val lightPink: Color = Color(0xFFF68299),
    val darkPink: Color = Color(0xff963E5A),
    val white: Color = Color(0xFFFFFFFF),
    val black: Color = Color(0xFF000000),
    val gray: Color = Color(0xFF929292),
    val textSelection: TextSelectionColors = TextSelectionColors(
        handleColor = Color(0xff963E5A),
        backgroundColor = Color(0xff963E5A),
    ),
    val disableButtonColor: Color = Color(0xFFFFD3D8),
    val errorRed: Color = Color(0xFFFF0000)
)

val LocalAppColors = staticCompositionLocalOf { AppColors() }