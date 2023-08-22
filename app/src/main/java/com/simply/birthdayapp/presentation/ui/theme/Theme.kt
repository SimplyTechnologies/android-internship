package com.simply.birthdayapp.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat
import com.simply.birthdayapp.R

@Composable
fun BirthdayAppTheme(
    isStatusBarAppearanceLight: Boolean = true,
    content: @Composable () -> Unit,
) {
    val appColors = AppColors(
        backgroundPink = Color(0xFFFFF5F4),
        lightPink = Color(0xFFF68299),
        darkPink = Color(0xff963E5A),
        white = Color(0xFFFFFFFF),
        black = Color(0xFF000000),
        gray = Color(0xFF929292),
    )
    val appTextSelectionColors = TextSelectionColors(
        handleColor = Color(0xff963E5A),
        backgroundColor = Color(0xff963E5A),
    )
    val appTypography = AppTypography(
        bold = TextStyle(fontFamily = FontFamily(Font(R.font.karma_bold))),
        medium = TextStyle(fontFamily = FontFamily(Font(R.font.karma_medium))),
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = appColors.backgroundPink.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isStatusBarAppearanceLight
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppTextSelectionColors provides appTextSelectionColors,
        LocalAppTypography provides appTypography,
        content = content,
    )
}

object BirthdayAppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val textSelectionColors: TextSelectionColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTextSelectionColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
}