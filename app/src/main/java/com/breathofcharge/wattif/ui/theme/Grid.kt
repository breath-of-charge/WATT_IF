package com.breathofcharge.wattif.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class WattIfGridSpec(
    val width: Dp,
    val height: Dp,
    val columns: Int,
    val margin: Dp,
    val gutter: Dp,
)

object WattIfGrid {
    val fold8 =
        WattIfGridSpec(
            width = 816.dp,
            height = 614.dp,
            columns = 6,
            margin = 32.dp,
            gutter = 16.dp,
        )

    val watch9 =
        WattIfGridSpec(
            width = 1920.dp,
            height = 1080.dp,
            columns = 8,
            margin = 160.dp,
            gutter = 24.dp,
        )
}
