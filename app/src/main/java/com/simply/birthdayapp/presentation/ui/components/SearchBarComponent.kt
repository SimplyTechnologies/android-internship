package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun SearchBarComponent(
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    shouldShowClear: Boolean = true,
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
                color = AppTheme.colors.gray,
                style = AppTheme.typography.medium,
            )
        },
        trailingIcon = {
            if (shouldShowClear && query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        tint = AppTheme.colors.gray,
                    )
                }
            } else {
                IconButton(onClick = onSearch) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(R.string.search),
                        tint = AppTheme.colors.gray,
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        singleLine = true,
        shape = AppTheme.shapes.circle,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colors.white,
            unfocusedContainerColor = AppTheme.colors.white,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = AppTheme.colors.darkPink,
            selectionColors = AppTheme.colors.textSelection,
        ),
        textStyle = AppTheme.typography.medium,
    )
}

@Preview
@Composable
private fun SearchBarComponentPreview() {
    SearchBarComponent()
}