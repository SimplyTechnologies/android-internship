package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BaseTextField(
    textState: String,
    label: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: Shape = TextFieldDefaults.shape,
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        modifier = Modifier.padding(top = 24.dp),
        value = textState,
        onValueChange = onValueChange,
        shape = shape,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            cursorColor = Color.Gray,
        ),
        placeholder = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.tertiary,
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = keyboardType,
        ),
        singleLine = true,
    )
}

@Preview(showBackground = false)
@Composable
private fun BaseTextFieldPreview() {
    BaseTextField(
        textState = "Name",
        label = "name",
        keyboardType = KeyboardType.Text,
        shape = RoundedCornerShape(13.dp)
    )
}