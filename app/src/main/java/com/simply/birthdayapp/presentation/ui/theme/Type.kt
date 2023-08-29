package com.simply.birthdayapp.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R

@Immutable
data class AppTypography(
    val bold: TextStyle = TextStyle(fontFamily = FontFamily(Font(R.font.karma_bold))),
    val medium: TextStyle = TextStyle(fontFamily = FontFamily(Font(R.font.karma_medium))),
    val boldKarmaDarkPink: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.karma_bold)),
        color = Color(0xff963E5A),
    ),
    val boldKarmaBlack: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.karma_bold)),
        color = Color(0xFF000000),
    ),
    val mediumKarmaBlack: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.karma_medium)),
        color = Color(0xFF000000),
    ),
    val mediumKarmaGray: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.karma_medium)),
        color = Color(0xFF929292),
    ),
    val mediumKarmaWhite: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.karma_medium)),
        color = Color(0xFFFFFFFF),
    ),
    val mediumKarmaDarkPink: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.karma_medium)),
        color = Color(0xff963E5A),
    ),
    val urlPrefix: SpanStyle = SpanStyle(
        fontSize = 20.sp,
        color = Color(0xFF000000),
        fontFamily = FontFamily(Font(R.font.karma_bold)),
    ),
    val url: SpanStyle = SpanStyle(
        fontSize = 16.sp,
        color = Color(0xFF000000),
        fontFamily = FontFamily(Font(R.font.karma_medium)),
        textDecoration = TextDecoration.Underline,
    ),
)

val LocalAppTypography = staticCompositionLocalOf { AppTypography() }