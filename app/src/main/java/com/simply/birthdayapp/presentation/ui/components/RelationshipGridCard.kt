package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun RelationshipGridCard(
    relationship: RelationshipEnum,
    selectedRelationship: RelationshipEnum?,
    onCardClick: () -> Unit = {},
) {
    Card(
        shape = AppTheme.shapes.circle,
        modifier = Modifier
            .padding(4.dp)
            .height(37.dp)
            .clip(AppTheme.shapes.circle)
            .clickable(onClick = onCardClick)
            .border(
                width = 2.dp,
                color = if (selectedRelationship == relationship) AppTheme.colors.darkPink else Color.Transparent,
                shape = AppTheme.shapes.circle,
            ),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.white),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = relationship.resId),
                style = AppTheme.typography.boldKarmaBlack,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
@Preview
private fun RelationshipGridCardPreview() {
    RelationshipGridCard(RelationshipEnum.BEST_FRIEND, RelationshipEnum.BEST_FRIEND)
}