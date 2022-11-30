package com.ajailani.projekan.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.R

@Composable
fun CaptionImage(
    modifier: Modifier = Modifier,
    image: Painter,
    caption: String,
    captionColor: Color = MaterialTheme.colors.onBackground,
    captionStyle: TextStyle = MaterialTheme.typography.body1
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier,
            painter = image,
            contentDescription = "Image with caption"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = caption,
            textAlign = TextAlign.Center,
            color = captionColor,
            style = captionStyle
        )
    }
}