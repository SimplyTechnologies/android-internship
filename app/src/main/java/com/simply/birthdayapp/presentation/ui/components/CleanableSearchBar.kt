package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CleanableSearchBar(
    query: String,
    onQueryChange: (String) -> Unit = {},
    active: Boolean,
    onActiveChange: (Boolean) -> Unit = {},
    content: @Composable (() -> Unit) = {},
) {
    val focusManager = LocalFocusManager.current

    SearchBar(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        query = query,
        onQueryChange = { onQueryChange(it) },
        onSearch = { focusManager.clearFocus() },
        active = active,
        onActiveChange = {
            onActiveChange(it)
            if (it.not()) onQueryChange("")
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                fontFamily = FontFamily(Font(R.font.karma_light)),
            )
        },
        trailingIcon = {
            if (active) {
                IconButton(
                    onClick = {
                        if (query.isNotEmpty()) onQueryChange("")
                        else onActiveChange(false)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search),
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            dividerColor = MaterialTheme.colorScheme.tertiary,
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                cursorColor = MaterialTheme.colorScheme.tertiary,
            )
        ),
    ) {
        content()
    }
}

@Preview
@Composable
private fun CleanableSearchBarPreview() {
    CleanableSearchBar(
        query = "",
        active = false,
    )
}