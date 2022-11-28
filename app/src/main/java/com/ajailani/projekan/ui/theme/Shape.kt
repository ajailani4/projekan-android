package com.ajailani.projekan.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(15.dp),
    large = RoundedCornerShape(20.dp)
)

val Shapes.extraLarge: CornerBasedShape
    @Composable
    get() = CircleShape