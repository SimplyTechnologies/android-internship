package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = FontFamily(Font(R.font.karm_light)),
    shape: RoundedCornerShape,
    buttonTitle: String,
    backgroundColor: Color = AppTheme.colors.lightPink,
    textColor: Color = AppTheme.colors.darkPink,
    enabled: Boolean = true,
    fontSize: TextUnit = 15.sp,
    textStyle: TextStyle = AppTheme.typography.bold,
    disabledContainerColor: Color = AppTheme.colors.disableButtonColor,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .height(51.dp)
            .fillMaxWidth(),
        shape = shape,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = disabledContainerColor
        ),
    ) {
        Text(
            text = buttonTitle,
            color = textColor,
            fontSize = fontSize,
            style = textStyle,
            fontFamily = fontFamily,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthButtonPreview() {
    AuthButton(
        shape = RoundedCornerShape(24.dp),
        buttonTitle = "Register",
    )
}