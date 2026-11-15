package com.breathofcharge.wattif.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Ink = Color(0xFF0E0E0F)
val MutedInk = Color(0xFF6B6B72)
val Canvas = Color(0xFFF6F6F7)
val SoftSurface = Color(0xFFEBEBED)
val OutlineSoft = Color(0xFFDBDBDD)
val NightCanvas = Color(0xFF111114)
val NightSurface = Color(0xFF1C1C20)

private val LightColors =
    lightColorScheme(
        primary = Ink,
        onPrimary = Color.White,
        background = Canvas,
        onBackground = Ink,
        surface = Color.White,
        onSurface = Ink,
        surfaceVariant = SoftSurface,
        onSurfaceVariant = MutedInk,
        outline = OutlineSoft,
    )

private val DarkColors =
    darkColorScheme(
        primary = Color.White,
        onPrimary = Ink,
        background = NightCanvas,
        onBackground = Color(0xFFF4F4F5),
        surface = NightSurface,
        onSurface = Color(0xFFF4F4F5),
        surfaceVariant = Color(0xFF29292E),
        onSurfaceVariant = Color(0xFFB7B7BF),
        outline = Color(0xFF3B3B42),
    )

private val WattIfTypography =
    Typography(
        displayLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 42.sp),
        headlineLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 29.sp),
        headlineMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 23.sp),
        titleLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 19.sp),
        titleMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
        bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        labelLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 14.sp),
    )

@Composable
fun WattIfTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = WattIfTypography,
        content = content,
    )
}
