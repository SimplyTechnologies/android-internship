package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoNetworkConnectionDialog(
    onDoNotShow: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(AppTheme.shapes.mediumRoundedCorners)
                .background(AppTheme.colors.backgroundPink)
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(R.string.network_status),
                fontSize = 25.sp,
                style = AppTheme.typography.boldKarmaBlack,
                maxLines = 1,
            )
            Text(
                text = stringResource(R.string.no_network_connection),
                fontSize = 15.sp,
                style = AppTheme.typography.mediumKarmaBlack,
                maxLines = 1,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = onDoNotShow,
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.darkPink),
                ) {
                    Text(
                        text = stringResource(R.string.do_not_show),
                        fontSize = 15.sp,
                        style = AppTheme.typography.mediumKarmaBlack,
                        maxLines = 1,
                    )
                }
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.lightPink),
                ) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        fontSize = 15.sp,
                        style = AppTheme.typography.mediumKarmaBlack,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NoNetworkConnectionDialogPreview() {
    NoNetworkConnectionDialog()
}