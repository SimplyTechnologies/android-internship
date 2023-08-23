package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    textState: String,
    label: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: Shape = TextFieldDefaults.shape,
    imeAction: ImeAction = ImeAction.Next,
    hasError: Boolean = false,
    errorText:String = "",
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        modifier = modifier,
        value = textState,
        onValueChange = onValueChange,
        shape = shape,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = AppTheme.colors.backgroundPink,
            focusedContainerColor = AppTheme.colors.backgroundPink,
            cursorColor = AppTheme.colors.gray,
        ),
        placeholder = {
            Text(
                text = label,
                color = AppTheme.colors.darkPink,
                color = MaterialTheme.colorScheme.tertiary,
                fontFamily = FontFamily(Font(R.font.karm_light)),
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType,
        ),
        singleLine = true,
    )
    if (hasError) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                textAlign = TextAlign.Start,
                text = errorText ,
                color = Color.Red,
                fontFamily = FontFamily(Font(resId = R.font.karm_light)),
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun BaseTextFieldPreview() {
    BaseTextField(
        textState = "Name",
        label = "name",
        keyboardType = KeyboardType.Text,
        shape = RoundedCornerShape(13.dp),
        imeAction = ImeAction.Next,
        hasError = false
    )
}