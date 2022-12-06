package com.ajailani.projekan.ui.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.ui.theme.Grey

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OutlinedChip(
    text: String,
    shape: Shape = CircleShape,
    border: BorderStroke = BorderStroke(width = 1.dp, color = Grey),
    color: Color = MaterialTheme.colors.surface,
    selectedColor: Color = MaterialTheme.colors.primary,
    textColor: Color = contentColorFor(color),
    selectedTextColor: Color = MaterialTheme.colors.primaryVariant,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        shape = shape,
        color = if (isSelected) selectedColor else color,
        border = if (isSelected) BorderStroke(width = 1.dp, color = selectedColor) else border,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .height(32.dp)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = if (isSelected) selectedTextColor else textColor,
                fontWeight = FontWeight.Medium,
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
fun PreviewOutlinedChip() {
    OutlinedChip(
        text = "Android",
        onClick = {}
    )
}

@Preview
@Composable
fun PreviewSelectedOutlinedChip() {
    OutlinedChip(
        text = "Android",
        isSelected = true,
        onClick = {}
    )
}