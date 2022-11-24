package com.ajailani.projekan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = light_primary,
    onPrimary = light_onPrimary,
    primaryVariant = light_primaryVariant,
    secondary = light_secondary,
    onSecondary = light_onSecondary,
    secondaryVariant = light_secondaryVariant,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface,
    error = light_error,
    onError = light_onError
)

private val DarkColorPalette = darkColors(
    primary = dark_primary,
    onPrimary = dark_onPrimary,
    primaryVariant = dark_primaryVariant,
    secondary = dark_secondary,
    onSecondary = dark_onSecondary,
    secondaryVariant = dark_secondaryVariant,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface,
    error = dark_error,
    onError = dark_onError
)

@Composable
fun ProjekanTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}