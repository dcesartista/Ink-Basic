package com.canvas.ui.default.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** T3 motion tokens. */
data class MotionTokens(
    val durationFast: Int = 100,
    val durationNormal: Int = 200,
    val durationSlow: Int = 500,
)

/** T3 sizing/density tokens. touchTarget is core-pinned (not a "look"). */
data class SizingTokens(
    val densityCompact: Dp = 40.dp,
    val densityComfortable: Dp = 56.dp,
    val touchTarget: Dp = 48.dp,
    val appBarHeight: Dp = 64.dp,
    val iconSize: Dp = 24.dp,
    val progressThickness: Dp = 4.dp,
)
