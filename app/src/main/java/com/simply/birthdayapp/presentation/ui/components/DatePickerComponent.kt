package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit = {},
    onConfirmButtonClick: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(
                    text = stringResource(id = R.string.confirm),
                    color = AppTheme.colors.darkPink,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissButtonClick) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = AppTheme.colors.darkPink,
                )
            }
        },
    ) {
        DatePicker(
            dateFormatter = DatePickerFormatter(),
            modifier = Modifier.padding(top = 16.dp),
            state = datePickerState,
            showModeToggle = false,
            title = null,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = AppTheme.colors.darkPink,
                selectedYearContainerColor = AppTheme.colors.darkPink,
                todayContentColor = AppTheme.colors.darkPink,
                currentYearContentColor = AppTheme.colors.darkPink,
                weekdayContentColor = AppTheme.colors.darkPink,
                headlineContentColor = AppTheme.colors.darkPink,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun DatePickerComponentPreview() {
    DatePickerComponent(rememberDatePickerState())
}