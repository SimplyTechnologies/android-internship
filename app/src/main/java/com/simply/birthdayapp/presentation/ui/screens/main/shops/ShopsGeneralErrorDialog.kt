package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.ShopsGeneralError
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun ShopsGeneralErrorDialog(
    error: ShopsGeneralError,
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        containerColor = AppTheme.colors.backgroundPink,
        titleContentColor = AppTheme.colors.black,
        textContentColor = AppTheme.colors.black,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.shops_related_error),
                fontSize = 25.sp,
                style = AppTheme.typography.boldKarmaBlack,
            )
        },
        text = {
            Text(
                text = stringResource(id = error.messageId),
                fontSize = 15.sp,
                style = AppTheme.typography.mediumKarmaBlack,
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.dismiss),
                    fontSize = 15.sp,
                    style = AppTheme.typography.boldKarmaBlack,
                )
            }
        },
    )
}

@Preview
@Composable
private fun ShopsGeneralErrorDialogPreview() {
    ShopsGeneralErrorDialog(error = ShopsGeneralError.FailedToLoadShops)
}