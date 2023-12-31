package com.simply.birthdayapp.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun RoundAsyncImage(
    modifier: Modifier = Modifier,
    data: Any? = null,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Painter? = painterResource(id = R.drawable.placeholder_person),
    error: Painter? = painterResource(id = R.drawable.placeholder_person),
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .build(),
        modifier = modifier
            .clip(AppTheme.shapes.circle)
            .border(
                color = borderColor,
                width = borderWidth,
                shape = AppTheme.shapes.circle,
            ),
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = placeholder,
        error = error,
    )
}

@Preview
@Composable
private fun RoundAsyncImagePreview() {
    RoundAsyncImage()
}