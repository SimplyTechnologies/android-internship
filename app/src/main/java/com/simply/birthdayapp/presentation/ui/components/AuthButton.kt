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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.Primary1
import com.simply.birthdayapp.presentation.ui.theme.Primary2

@Composable
fun AuthButton(
    shape: RoundedCornerShape,
    buttonTitle: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .height(51.dp)
            .fillMaxWidth(),
        shape = shape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
    ) {
        Text(
            text = buttonTitle,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.karm_light)),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterButtonPreview() {
    AuthButton(
        shape = RoundedCornerShape(24.dp),
        buttonTitle = "Register",
        backgroundColor = Primary2,
        textColor = Primary1,
    )
}