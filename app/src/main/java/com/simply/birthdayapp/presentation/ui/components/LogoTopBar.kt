package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R

@Composable
fun LogoTopBar(
    logoHorizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = logoHorizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .width(88.dp)
                .height(40.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.birthday_app_logo),
        )
    }
}

@Preview
@Composable
private fun LogoTopBarPreview() {
    LogoTopBar()
}