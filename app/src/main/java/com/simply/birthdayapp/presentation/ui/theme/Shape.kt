package com.simply.birthdayapp.presentation.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class AppShapes(
    val risingStartRoundedEndCorners: RoundedCornerShape = RoundedCornerShape(
        topStart = 42.dp,
        topEnd = 26.dp,
        bottomEnd = 26.dp
    ),
    val risingEndRoundedStartCorners: RoundedCornerShape = RoundedCornerShape(
        bottomEnd = 42.dp,
        topStart = 26.dp,
        bottomStart = 26.dp
    ),
    val circle: RoundedCornerShape = CircleShape,
    val mediumRoundedCorners: RoundedCornerShape = RoundedCornerShape(25.dp),
    val smallRoundedCorners: RoundedCornerShape = RoundedCornerShape(13.dp),
)

val LocalAppShapes = staticCompositionLocalOf { AppShapes() }