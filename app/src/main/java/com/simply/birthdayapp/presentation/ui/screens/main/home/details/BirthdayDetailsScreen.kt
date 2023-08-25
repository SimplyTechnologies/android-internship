package com.simply.birthdayapp.presentation.ui.screens.main.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.Birthday
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun BirthdayDetailsScreen(birthday: Birthday) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgroundPink),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBaseTopBar()
        Icon(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(top = 16.dp, end = 24.dp),
            painter = painterResource(id = R.drawable.ic_pen),
            contentDescription = "Pen icon",
        )
        RoundAsyncImage(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(top = 4.dp),
            data = birthday.image,
            borderWidth = 0.dp,
            borderColor = Color.Transparent,
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = birthday.name,
            fontSize = 20.sp,
            style = AppTheme.typography.bold,
            color = AppTheme.colors.black
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = birthday.date,
            fontSize = 14.sp,
            style = AppTheme.typography.bold,
            color = AppTheme.colors.black
        )
        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.relationship) + " :",
                fontSize = 14.sp,
                style = AppTheme.typography.bold,
                color = AppTheme.colors.black,
            )
            Card(
                modifier = Modifier
                    .height(37.dp)
                    .padding(start = 2.dp),
                shape = AppTheme.shapes.tinyRoundedCorners,
                colors = CardDefaults.cardColors(AppTheme.colors.white),
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = birthday.relation,
                        fontSize = 14.sp,
                        style = AppTheme.typography.bold,
                        color = AppTheme.colors.black,
                    )
                }
            }
        }

    }
}

@Composable
@Preview
private fun BirthdayDetailsScreenPreview() {
    BirthdayDetailsScreen(
        Birthday(
            name = "Suzy Blacks",
            image = byteArrayOf(),
            relation = "Best friend",
            date = "05.11.2010"
        )
    )
}