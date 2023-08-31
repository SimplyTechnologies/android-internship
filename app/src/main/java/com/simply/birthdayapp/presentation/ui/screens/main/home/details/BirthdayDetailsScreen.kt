package com.simply.birthdayapp.presentation.ui.screens.main.home.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.extensions.fromUtcToDayMonthYearDate
import com.simply.birthdayapp.presentation.extensions.sendMessage
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.GenerateMessageDialog
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.screens.main.home.birthday.BirthdayViewModel
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun BirthdayDetailsScreen(
    birthdayViewModel: BirthdayViewModel,
    birthdayDetailsViewModel: BirthdayDetailsViewModel,
    navigateToShopsScreen: () -> Unit = {},
    navigateToBirthdayScreen: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val birthday by birthdayDetailsViewModel.birthday.collectAsState()
    val birthdayMessage by birthdayDetailsViewModel.birthdayMessage.collectAsState()
    var showGenerateMessageDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler { onBackClick() }

    Column {
        AppBaseTopBar(onBackClick = onBackClick)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.backgroundPink),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(top = 16.dp, end = 24.dp),
                onClick = {
                    birthdayViewModel.setBirthday(birthday = birthday)
                    navigateToBirthdayScreen()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pen),
                    contentDescription = "Pen icon",
                )
            }
            RoundAsyncImage(
                modifier = Modifier.size(100.dp),
                data = birthday?.image ?: "",
                placeholder = painterResource(id = R.drawable.placeholder_person),
                error = painterResource(id = R.drawable.placeholder_person),
            )
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = birthday?.name ?: "",
                fontSize = 20.sp,
                style = AppTheme.typography.bold,
                color = AppTheme.colors.black,
            )
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = birthday?.dateUtc?.fromUtcToDayMonthYearDate() ?: "",
                fontSize = 14.sp,
                style = AppTheme.typography.bold,
                color = AppTheme.colors.black,
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
                            text = context.getString(birthday?.relation?.resId ?: RelationshipEnum.BEST_FRIEND.resId),
                            fontSize = 14.sp,
                            style = AppTheme.typography.bold,
                            color = AppTheme.colors.black,
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 128.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
            ) {
                Button(
                    modifier = Modifier.height(41.dp),
                    shape = AppTheme.shapes.smallRoundedCorners,
                    onClick = { showGenerateMessageDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.lightPink,
                    ),
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = stringResource(id = R.string.generate_message),
                        style = AppTheme.typography.boldKarmaDarkPink,
                        fontSize = 18.sp,
                    )
                }
                Button(
                    modifier = Modifier.height(41.dp),
                    shape = AppTheme.shapes.smallRoundedCorners,
                    onClick = navigateToShopsScreen,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.darkPink,
                    ),
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = stringResource(id = R.string.find_gift),
                        style = AppTheme.typography.bold,
                        color = AppTheme.colors.lightPink,
                        fontSize = 18.sp,
                    )
                }
            }
            if (showGenerateMessageDialog) {
                GenerateMessageDialog(
                    message = birthdayMessage,
                    onValueChange = { message -> birthdayDetailsViewModel.setBirthdayMessage(message) },
                    onDismissRequest = { showGenerateMessageDialog = false },
                    onConfirmButtonClick = {
                        context.sendMessage(birthdayMessage)
                        showGenerateMessageDialog = false
                    },
                )
            }
        }
    }
}

@Composable
@Preview
private fun BirthdayDetailsScreenPreview() {
    BirthdayDetailsScreen(getViewModel(), getViewModel())
}