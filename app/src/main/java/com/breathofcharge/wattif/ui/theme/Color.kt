package com.breathofcharge.wattif.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val primary = Color(0xFFD1F0FF)

val gray900 = Color(0xFF0F1011)
val gray800 = Color(0xFF1F2023)
val gray700 = Color(0xFF26282B)
val gray600 = Color(0xFF3E4046)
val gray500 = Color(0xFF4D5057)
val gray400 = Color(0xFF717379)
val gray300 = Color(0xFF94969A)
val gray200 = Color(0xFFB8B9BC)
val gray100 = Color(0xFFDBDCDD)
val white = Color(0xFFFFFFFF)

val fieldBackground = Color(0x80EDEDEE)
val darkIcon = Color(0x993E4046)

object WattIfStatusBrushes {
    val good70Background = Color(0xFFE8F4FF)
    val good70 =
        Brush.radialGradient(
            0.0f to Color(0xFFFFF871),
            0.25f to Color(0xFFE1F155),
            0.5f to Color(0xFFC3EB39),
            0.75f to Color(0xFFA4E41E),
            0.875f to Color(0xFF95E110),
            1.0f to Color(0xFF86DE02),
        )

    val normal50Background = Color(0xFF009FFC)
    val normal50 =
        Brush.radialGradient(
            0.0f to Color(0xFFFFF871),
            1.0f to Color(0xFFF9D33F),
        )

    val bad30Background = Color(0xFF54C3FF)
    val bad30 =
        Brush.radialGradient(
            0.0f to Color(0xFFFF7671),
            0.5f to Color(0xFFFCA458),
            1.0f to Color(0xFFF9D33F),
        )
}

// 기존 화면과의 호환을 유지하는 의미 기반 별칭입니다.
val Ink = gray900
val MutedInk = gray400
val Canvas = white
val SoftSurface = fieldBackground
val OutlineSoft = gray100
val NightCanvas = gray900
val NightSurface = gray800
