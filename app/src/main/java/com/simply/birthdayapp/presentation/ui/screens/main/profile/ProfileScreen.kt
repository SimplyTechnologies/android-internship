package com.simply.birthdayapp.presentation.ui.screens.main.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.AuthButton
import com.simply.birthdayapp.presentation.ui.components.CircularProgress
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onSignOutClicked: () -> Unit = {},
    navToChangePasswordScreen: () -> Unit = {},
    navToEditAccountScreen: () -> Unit = {},
) {
    val context = LocalContext.current
    val userProfile by profileViewModel.profile.collectAsState()
    val showLoading by profileViewModel.isOnLoadingState.collectAsState()
    val getUserErrorMessage by profileViewModel.getUserErrorMessage.collectAsState()
    val getUserErrorState by profileViewModel.getUserErrorState.collectAsState()

    LaunchedEffect(getUserErrorMessage) {
        if (getUserErrorMessage.isNotEmpty()) {
            Toast.makeText(context, (R.string.edit_account_failed), Toast.LENGTH_SHORT).show()
            profileViewModel.getUserErrorMessage()
        }
    }

    if (getUserErrorState) {
        AlertDialog(
            containerColor = AppTheme.colors.white,
            titleContentColor = AppTheme.colors.black,
            textContentColor = AppTheme.colors.black,
            onDismissRequest = { profileViewModel.getAccountErrorStare() },
            title = { Text(text = stringResource(id = R.string.get_user_error)) },
            text = { Text(text = stringResource(R.string.edit_account_network_error)) },
            confirmButton = {
                TextButton(onClick = { profileViewModel.getAccountErrorStare() }) {
                    Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
                }
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppBaseTopBar(
            horizontalArrangement = Arrangement.End,
            hasBackButton = false
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.backgroundPink)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                RoundAsyncImage(
                    modifier = Modifier.size(100.dp),
                    borderWidth = 1.dp,
                    borderColor = AppTheme.colors.gray,
                    placeholder = painterResource(id = R.drawable.placeholder_user_avatar),
                    error = painterResource(id = R.drawable.placeholder_user_avatar),
                    data = userProfile?.image
                )
                Text(
                    text = "${userProfile?.firstName}  ${userProfile?.lastName}",
                    fontSize = 20.sp,
                    style = AppTheme.typography.boldKarmaDarkPink,
                )
                Text(
                    text = userProfile?.email ?: "",
                    fontSize = 20.sp,
                    style = AppTheme.typography.boldKarmaDarkPink,
                )
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxSize()
                        .background(color = AppTheme.colors.backgroundPink),
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AuthButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .fillMaxWidth(),
                        textModifier = Modifier.fillMaxWidth(),
                        shape = AppTheme.shapes.smallRoundedCorners,
                        buttonTitle = stringResource(R.string.edit_account),
                        fontSize = 20.sp,
                        backgroundColor = AppTheme.colors.white,
                    ) {
                        navToEditAccountScreen()
                    }
                    AuthButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .fillMaxWidth(),
                        textModifier = Modifier.fillMaxWidth(),
                        shape = AppTheme.shapes.smallRoundedCorners,
                        fontSize = 20.sp,
                        buttonTitle = stringResource(R.string.change_password),
                        backgroundColor = AppTheme.colors.white
                    ) {
                        navToChangePasswordScreen()
                    }
                    AuthButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .fillMaxWidth(),
                        textModifier = Modifier.fillMaxWidth(),
                        shape = AppTheme.shapes.smallRoundedCorners,
                        fontSize = 20.sp,
                        buttonTitle = stringResource(R.string.sign_out),
                        backgroundColor = AppTheme.colors.white,
                    ) {
                        profileViewModel.signOut()
                        onSignOutClicked()
                    }
                }
            }
            if (showLoading)
                CircularProgress()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(profileViewModel = getViewModel())
}