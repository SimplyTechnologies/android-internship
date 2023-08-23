package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun RoundAsyncImage(
    modifier: Modifier = Modifier,
    data: Any? = null,
    borderColor: Color,
    borderWidth: Dp,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Painter? = null,
    error: Painter? = null,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .size(Size.ORIGINAL)
            .build(),
        modifier = modifier
            .clip(CircleShape)
            .border(color = borderColor, width = borderWidth, shape = CircleShape),
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = placeholder,
        error = error,
    )
}

@Preview
@Composable
private fun RoundAsyncImagePreview() {
    RoundAsyncImage(
        borderColor = MaterialTheme.colorScheme.tertiary,
        borderWidth = 1.dp,
    )
}