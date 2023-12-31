package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun PasswordTextFiled(
    modifier: Modifier = Modifier,
    textState: String = "",
    label: String = "",
    imeAction: ImeAction = ImeAction.Next,
    hasPasswordError: Boolean = false,
    inputMaxLength: Int = 30,
    shape: Shape = AppTheme.shapes.smallRoundedCorners,
    focusedContainerColor: Color = AppTheme.colors.backgroundPink,
    unfocusedContainerColor: Color = AppTheme.colors.backgroundPink,
    errorText: String = "",
    onValueChange: (String) -> Unit = {},
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = textState,
        onValueChange = {
            if (it.length <= inputMaxLength)
                onValueChange(it)
        },
        modifier = modifier,
        shape = shape,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = unfocusedContainerColor,
            focusedContainerColor = focusedContainerColor,
            cursorColor = Color.Gray,
        ),
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    isPasswordVisible = !isPasswordVisible
                }
            ) {
                Icon(
                    tint = AppTheme.colors.darkPink,
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = stringResource(id = R.string.password_icon_description),
                )
            }
        },
        placeholder = {
            Text(
                text = label,
                color = AppTheme.colors.darkPink,
                fontFamily = FontFamily(Font(R.font.karm_light)),
            )
        },
    )
    if (hasPasswordError) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                textAlign = TextAlign.Start,
                text = errorText,
                color = AppTheme.colors.errorRed,
                fontFamily = FontFamily(Font(R.font.karm_light)),
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordPreview() {
    PasswordTextFiled(
        modifier = Modifier.padding(top = 24.dp),
        textState = "Password"
    )
}