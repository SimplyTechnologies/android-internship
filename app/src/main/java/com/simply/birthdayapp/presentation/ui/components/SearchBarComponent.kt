package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R

@Composable
fun SearchBarComponent(
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth(),
        value = query,
        onValueChange = { onQueryChange(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                fontFamily = FontFamily(Font(R.font.karma_medium)),
                color = Color.Gray,
            )
        },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.tertiary,
                backgroundColor = MaterialTheme.colorScheme.tertiary,
            ),
        ),
    )
}

@Preview
@Composable
private fun SearchBarComponentPreview() {
    SearchBarComponent()
}