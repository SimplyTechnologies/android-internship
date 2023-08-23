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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun LandingButton(
    shape: RoundedCornerShape,
    buttonTitle: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = Modifier
            .padding(3.dp)
            .height(51.dp)
            .fillMaxWidth(),
        shape = shape,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
    ) {
        Text(
            text = buttonTitle,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.karm_light))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LandingButtonPreview() {
    LandingButton(
        shape = AppTheme.shapes.risingStartRoundedEndCorners,
        buttonTitle = stringResource(id = R.string.sign_in),
        backgroundColor = AppTheme.colors.lightPink,
        textColor = AppTheme.colors.darkPink,
    )
}