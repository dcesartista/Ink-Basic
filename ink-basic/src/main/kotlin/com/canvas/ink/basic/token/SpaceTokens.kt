package com.canvas.ink.basic.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * T3 spacing tokens — 4-unit base scale. The seven canonical steps (4/8/12/16/
 * 24/32/48) are the contract (ADR-0001). The extended sub-steps (2/6/20/40/80)
 * round the scale out for components that need finer or larger granularity
 * without breaking the base scale.
 */
data class SpaceTokens(
    val xxs: Dp = 4.dp,
    val xs: Dp = 8.dp,
    val sm: Dp = 12.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp,
    val xxxs: Dp = 2.dp,
    val xxs2: Dp = 6.dp,
    val sm2: Dp = 20.dp,
    val xxxl: Dp = 40.dp,
    val xxxxl: Dp = 80.dp,
    val layout: Layout = Layout(),
) {
    /** Screen-level layout gutters. */
    data class Layout(
        val page: Dp = 16.dp,
        val section: Dp = 24.dp,
        val item: Dp = 12.dp,
    )
}
