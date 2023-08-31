package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun ProfileButton(
    buttonTitle: String,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth(),
        shape = AppTheme.shapes.smallRoundedCorners,
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.white),
        onClick = onClick,
    )
    {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buttonTitle,
            color = AppTheme.colors.darkPink,
            fontSize = 20.sp,
            style = AppTheme.typography.bold,
            fontFamily = FontFamily(Font(R.font.karm_light)),
        )
    }
}