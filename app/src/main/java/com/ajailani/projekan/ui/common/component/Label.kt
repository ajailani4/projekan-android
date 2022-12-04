package com.ajailani.projekan.ui.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Label(
    title: String,
    backgroundColor: Color,
    textColor: Color,
    textStyle: TextStyle = MaterialTheme.typography.body2
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Text(
                text = title,
                color = textColor,
                style = textStyle.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}