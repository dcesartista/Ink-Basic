package com.canvas.ink.basic.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** T3 spacing tokens — base 4-unit scale: 0/4/8/12/16/24/32/48/64. */
data class SpaceTokens(
    val xxs: Dp = 4.dp,
    val xs: Dp = 8.dp,
    val sm: Dp = 12.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp,
    val layout: Layout = Layout(),
) {
    /** Screen-level layout gutters. */
    data class Layout(
        val page: Dp = 16.dp,
        val section: Dp = 24.dp,
        val item: Dp = 12.dp,
    )
}
