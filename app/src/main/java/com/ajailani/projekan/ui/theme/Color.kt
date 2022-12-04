package com.ajailani.projekan.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

// Light Theme
val light_primary = Color(0xFFFB9241)
val light_onPrimary = Color(0xFFFFFFFF)
val light_primaryVariant = Color(0xFFB86322)
val light_secondary = Color(0xFF2EC56C)
val light_onSecondary = Color(0xFFFFFFFF)
val light_secondaryVariant = Color(0xFF157E40)
val light_background = Color(0xFFFFFFFF)
val light_onBackground = Color(0xFF000000)
val light_surface = Color(0xFFFFFFFF)
val light_onSurface = Color(0xFF000000)
val light_error = Color(0xFFCF082B)
val light_onError = Color(0xFFFFFFFF)

// Dark Theme
val dark_primary = Color(0xFFFB9241)
val dark_onPrimary = Color(0xFFFFFFFF)
val dark_primaryVariant = Color(0xFFB86322)
val dark_secondary = Color(0xFF2EC56C)
val dark_onSecondary = Color(0xFFFFFFFF)
val dark_secondaryVariant = Color(0xFF157E40)
val dark_background = Color(0xFF121212)
val dark_onBackground = Color(0xFFFFFFFF)
val dark_surface = Color(0xFF2F2F2F)
val dark_onSurface = Color(0xFFFFFFFF)
val dark_error = Color(0xFFCF082B)
val dark_onError = Color(0xFFFFFFFF)

// Additional
val Colors.backgroundGrey: Color
    get() = if (isLight) Color(0xFFF0F0F0) else Color(0xFF121212)

val LightGrey = Color(0xFFDEDEDE)
val Grey = Color(0xFFBDBDBD)
val BackgroundShimmer = Color(0xFFA3A3A3)
val Blue = Color(0xFF249CDF)
val Yellow = Color(0xFFECB500)