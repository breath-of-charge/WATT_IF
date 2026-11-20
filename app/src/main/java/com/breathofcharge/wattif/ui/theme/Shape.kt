package com.breathofcharge.wattif.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object WattIfRadius {
    val largeBox = 40.dp
    val smallBox = 32.dp
    val button = 20.dp
}

val wattIfShapes =
    Shapes(
        small = RoundedCornerShape(WattIfRadius.button),
        medium = RoundedCornerShape(WattIfRadius.smallBox),
        large = RoundedCornerShape(WattIfRadius.largeBox),
        extraLarge = RoundedCornerShape(WattIfRadius.largeBox),
    )
