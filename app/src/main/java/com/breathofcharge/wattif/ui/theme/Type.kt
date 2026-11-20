package com.breathofcharge.wattif.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.breathofcharge.wattif.R

val wantedSans =
    FontFamily(
        Font(R.font.wanted_sans_regular, weight = FontWeight.Normal),
        Font(R.font.wanted_sans_medium, weight = FontWeight.Medium),
        Font(R.font.wanted_sans_semibold, weight = FontWeight.SemiBold),
    )

val doto =
    FontFamily(
        Font(R.font.doto_variable, weight = FontWeight.SemiBold),
    )

private fun wantedSansStyle(
    size: Int,
    weight: FontWeight,
) = TextStyle(
    fontFamily = wantedSans,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = 1.3.em,
    letterSpacing = (-0.02).em,
)

private fun dotoStyle(size: Int) =
    TextStyle(
        fontFamily = doto,
        fontWeight = FontWeight.SemiBold,
        fontSize = size.sp,
        lineHeight = 1.3.em,
        letterSpacing = (-0.02).em,
    )

object WattIfType {
    val a0SemiBold = wantedSansStyle(size = 112, weight = FontWeight.SemiBold)
    val d0SemiBold = wantedSansStyle(size = 64, weight = FontWeight.SemiBold)
    val d1SemiBold = wantedSansStyle(size = 48, weight = FontWeight.SemiBold)
    val d2SemiBold = wantedSansStyle(size = 36, weight = FontWeight.SemiBold)

    val b0SemiBold = wantedSansStyle(size = 32, weight = FontWeight.SemiBold)
    val b0Medium = wantedSansStyle(size = 32, weight = FontWeight.Medium)
    val b1SemiBold = wantedSansStyle(size = 28, weight = FontWeight.SemiBold)
    val b2SemiBold = wantedSansStyle(size = 24, weight = FontWeight.SemiBold)
    val b3SemiBold = wantedSansStyle(size = 20, weight = FontWeight.SemiBold)

    val caption1SemiBold = wantedSansStyle(size = 22, weight = FontWeight.SemiBold)
    val caption1Medium = wantedSansStyle(size = 22, weight = FontWeight.Medium)
    val caption1Regular = wantedSansStyle(size = 22, weight = FontWeight.Normal)
    val caption2SemiBold = wantedSansStyle(size = 20, weight = FontWeight.SemiBold)
    val caption2Medium = wantedSansStyle(size = 20, weight = FontWeight.Medium)
    val caption2Regular = wantedSansStyle(size = 20, weight = FontWeight.Normal)

    val dotoD0SemiBold = dotoStyle(size = 112)
    val dotoD1SemiBold = dotoStyle(size = 48)
}

val wattIfTypography =
    Typography(
        displayLarge = WattIfType.a0SemiBold,
        displayMedium = WattIfType.d0SemiBold,
        displaySmall = WattIfType.d1SemiBold,
        headlineLarge = WattIfType.d2SemiBold,
        titleLarge = WattIfType.b0SemiBold,
        titleMedium = WattIfType.b1SemiBold,
        titleSmall = WattIfType.b2SemiBold,
        bodyLarge = WattIfType.b3SemiBold,
        bodyMedium = WattIfType.caption1Regular,
        bodySmall = WattIfType.caption2Regular,
        labelLarge = WattIfType.caption1SemiBold,
        labelMedium = WattIfType.caption1Medium,
        labelSmall = WattIfType.caption2Medium,
    )
