package com.simply.birthdayapp.presentation.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun BirthdayAppTheme(
    isStatusBarAppearanceLight: Boolean = true,
    content: @Composable () -> Unit,
) {
    val appColors = AppColors()
    val appTypography = AppTypography()

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
        LocalAppTypography provides appTypography,
        content = content,
    )
}

object BirthdayAppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
}