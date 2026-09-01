package com.canvas.uidefault.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** T3 radius tokens — steps 0/2/4/8/12/16/999 (pill). */
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
