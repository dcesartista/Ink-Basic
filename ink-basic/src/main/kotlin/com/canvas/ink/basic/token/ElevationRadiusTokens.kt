package com.canvas.ink.basic.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** T3 radius tokens — one step per token: 0/4/8/12/999 (pill) (ADR-0001). */
data class RadiusTokens(
    val none: Dp = 0.dp,
    val sm: Dp = 4.dp,
    val md: Dp = 8.dp,
    val lg: Dp = 12.dp,
    val pill: Dp = 999.dp,
)

/**
 * T3 elevation tokens. Each level = shadow elevation + tonal surface delta,
 * so a raised surface shifts tone consistently with its depth.
 */
data class ElevationTokens(
    val flat: Dp = 0.dp,
    val sm: Dp = 2.dp,
    val md: Dp = 6.dp,
    val lg: Dp = 12.dp,
)
