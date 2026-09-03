package com.canvas.ink.basic.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * T3 border width tokens. Stroke width is not a "look" decision, so these are
 * identical across every mode and palette (ADR-0001 Border).
 */
data class BorderTokens(
    val thin: Dp = 1.dp,
    val medium: Dp = 2.dp,
    val thick: Dp = 4.dp,
)
