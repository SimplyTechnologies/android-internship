package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.simply.birthdayapp.R

@Composable
fun RoundAsyncImage(
    modifier: Modifier = Modifier,
    data: Any? = null,
    borderColor: Color,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .size(Size.ORIGINAL)
            .build(),
        modifier = modifier
            .clip(CircleShape)
            .border(color = borderColor, width = 1.dp, shape = CircleShape),
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder),
    )
}

@Preview
@Composable
private fun RoundAsyncImagePreview() {
    RoundAsyncImage(
        borderColor = MaterialTheme.colorScheme.tertiary,
    )
}