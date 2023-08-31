package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun GenerateMessageDialog(
    message: String = "",
    onValueChange: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onConfirmButtonClick: () -> Unit = {},
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.colors.backgroundPink,
                    ),
                    shape = AppTheme.shapes.tinyRoundedCorners,
                ) {
                    Text(
                        text = stringResource(id = R.string.send),
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp),
                        style = AppTheme.typography.boldKarmaDarkPink,
                        fontSize = 16.sp,
                    )
                }
            }
        },
        text = {
            Card(
                modifier = Modifier
                    .height(164.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.colors.backgroundPink,
                ),
                shape = AppTheme.shapes.tinyRoundedCorners,
            ) {
                BasicTextField(
                    modifier = Modifier.padding(16.dp),
                    value = message,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(resId = R.font.karma_bold)),
                        color = AppTheme.colors.darkGray,
                    )
                )
            }
        },
    )
}

@Composable
@Preview
private fun GenerateMessageDialogPreview() {
    GenerateMessageDialog()
}