package com.simply.birthdayapp.presentation.ui.screens.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.AuthButton
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun ProfileScreen(){
    Scaffold(
        topBar = { AppBaseTopBar() }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPink),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPink),
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            ){

            RoundAsyncImage(
                modifier = Modifier.size(100.dp),
                borderWidth = 1.dp,
                borderColor = AppTheme.colors.gray,
                placeholder = painterResource(id = R.drawable.placeholder_user_avatar)
            )
            Text(
                text = "Name",
                fontSize = 20.sp,
                style = AppTheme.typography.boldKarmaDarkPink,
            )
            Text(
                text ="Email",
                fontSize = 20.sp,
                style = AppTheme.typography.boldKarmaDarkPink,
            )
            AuthButton(
                shape = AppTheme.shapes.smallRoundedCorners,
                buttonTitle = "Edit Account",
                fontSize = 20.sp,
                backgroundColor = AppTheme.colors.white
            )
            AuthButton(
                shape = AppTheme.shapes.smallRoundedCorners,
                fontSize = 20.sp,
                buttonTitle = "Sign Out",
                textStyle = AppTheme.typography.boldKarmaDarkPink,
                backgroundColor = AppTheme.colors.white
            )
        }
    }}
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}