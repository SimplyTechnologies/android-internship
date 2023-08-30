package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun CalendarPermissionDialog(
    onDismissRequest: () -> Unit = {},
    onConfirmButtonClick: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text(text = stringResource(id = R.string.calendar_permission_explanation)) },
        dismissButton = {
            TextButton(onClick = onDismissButtonClick) {
                Text(text = stringResource(id = R.string.cancel), color = AppTheme.colors.black)
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
            }
        },
    )
}

@Composable
@Preview
private fun CalendarPermissionDialogPreview() {
    CalendarPermissionDialog()
}