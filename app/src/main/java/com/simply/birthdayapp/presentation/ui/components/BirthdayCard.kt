package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.extensions.fromUtcToDayMonthYearDate
import com.simply.birthdayapp.presentation.models.Birthday
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import com.simply.birthdayapp.presentation.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayCard(
    birthday: Birthday,
    onCardClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = AppTheme.shapes.mediumRoundedCorners,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.white,
        ),
        onClick = onCardClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundAsyncImage(
                modifier = Modifier.size(70.dp),
                data = birthday.image,
                contentDescription = birthday.name,
                placeholder = painterResource(id = R.drawable.placeholder_person),
                error = painterResource(id = R.drawable.placeholder_person),
            )
            Column(modifier = Modifier.padding(start = 30.dp)) {
                Text(
                    text = birthday.name,
                    fontSize = 20.sp,
                    style = AppTheme.typography.boldKarmaBlack,
                )
                Text(
                    text = birthday.dateUtc.fromUtcToDayMonthYearDate(),
                    fontSize = 14.sp,
                    style = AppTheme.typography.boldKarmaBlack,
                )
            }
        }
    }
}

@Composable
@Preview
private fun BirthdayCardPreview() {
    BirthdayCard(
        Birthday(
            id = 1,
            name = "Suzy Black",
            image = byteArrayOf(),
            relation = RelationshipEnum.BEST_FRIEND,
            dateUtc = "05.11.2010"
        )
    )
}