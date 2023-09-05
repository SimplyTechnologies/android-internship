package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    rating: Double = 0.0,
    numOfStars: Int = 5,
    starColor: Color = AppTheme.colors.orange,
    starSize: Dp = 20.dp,
) {
    val numOfFilledStars = floor(rating).toInt()
    val numOfEmptyStars = (numOfStars - ceil(rating)).toInt()
    val numOfStarHalves = if (rating.rem(1).equals(0.0)) 0 else 1

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(numOfFilledStars) {
            Icon(
                modifier = Modifier.size(starSize),
                imageVector = Icons.Outlined.Star,
                contentDescription = stringResource(R.string.filled_star),
                tint = starColor,
            )
        }
        repeat(numOfStarHalves) {
            Icon(
                modifier = Modifier.size(starSize),
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = stringResource(R.string.star_half),
                tint = starColor,
            )
        }
        repeat(numOfEmptyStars) {
            Icon(
                modifier = Modifier.size(starSize),
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = stringResource(R.string.empty_star),
                tint = starColor,
            )
        }
    }
}

@Preview
@Composable
private fun FilledRatingBarPreview() {
    RatingBar(
        rating = 5.0,
        numOfStars = 5,
    )
}

@Preview
@Composable
private fun HalvedRatingBarPreview() {
    RatingBar(
        rating = 2.5,
        numOfStars = 5,
    )
}

@Preview
@Composable
private fun EmptyRatingBarPreview() {
    RatingBar(
        rating = 0.0,
        numOfStars = 5,
    )
}