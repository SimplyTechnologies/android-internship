package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R

@Composable
fun AppBaseTopBar(
    onBackClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.padding(start = 16.dp),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "contentDescription",
                tint = MaterialTheme.colorScheme.tertiary,
            )
        }
        Image(
            modifier = Modifier.padding(end = 24.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun AppBaseTopBarPreview() {
    AppBaseTopBar()
}