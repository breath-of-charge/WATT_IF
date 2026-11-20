package com.breathofcharge.wattif.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors =
    lightColorScheme(
        primary = gray900,
        onPrimary = white,
        primaryContainer = primary,
        onPrimaryContainer = gray900,
        background = white,
        onBackground = gray900,
        surface = white,
        onSurface = gray900,
        surfaceVariant = fieldBackground,
        onSurfaceVariant = gray500,
        outline = gray100,
    )

private val DarkColors =
    darkColorScheme(
        primary = primary,
        onPrimary = gray900,
        primaryContainer = gray700,
        onPrimaryContainer = primary,
        background = gray900,
        onBackground = white,
        surface = gray800,
        onSurface = white,
        surfaceVariant = gray700,
        onSurfaceVariant = gray200,
        outline = gray600,
    )

@Composable
fun WattIfTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = wattIfTypography,
        shapes = wattIfShapes,
        content = content,
    )
}
