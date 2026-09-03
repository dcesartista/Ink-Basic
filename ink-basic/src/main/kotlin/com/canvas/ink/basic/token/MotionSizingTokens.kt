package com.canvas.ink.basic.token

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * T3 motion tokens — durations 100/200/500ms, one per token, plus the three
 * easing curves the contract requires (ADR-0001). An implementation missing
 * easing cannot animate on-spec, so these are not optional.
 *
 * The curves are defined here as explicit beziers rather than borrowed from
 * Material, so a palette swapping them does not inherit a framework feel.
 */
data class MotionTokens(
    val durationFast: Int = 100,
    val durationNormal: Int = 200,
    val durationSlow: Int = 500,
    /** Symmetric ease for movement that starts and ends on screen. */
    val easingStandard: Easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f),
    /** Entering: fast in, settling out. */
    val easingDecelerate: Easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f),
    /** Exiting: gentle start, accelerating away. */
    val easingAccelerate: Easing = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f),
)

/** T3 sizing/density tokens. touchTarget is core-pinned (not a "look"). */
data class SizingTokens(
    val densityCompact: Dp = 40.dp,
    val densityComfortable: Dp = 56.dp,
    val touchTarget: Dp = 48.dp,
    val appBarHeight: Dp = 64.dp,
    val iconSize: Dp = 24.dp,
    val iconSizeSm: Dp = 16.dp,
    val iconSizeLg: Dp = 32.dp,
    val progressThickness: Dp = 4.dp,
)
