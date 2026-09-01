package com.canvas.uidefault.token

import androidx.compose.ui.graphics.Color

/**
 * T3 color token contract. Every semantic color, with its `on*` contrast pair
 * for accent/state colors (the shared on* contract — ADR-0001).
 */
data class ColorTokens(
    val bgSurface: Color,
    val bgSurfaceAlt: Color,
    val bgSurfaceRaised: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val textInverse: Color,
    val accentPrimary: Color,
    val accentOnPrimary: Color,
    val accentSecondary: Color,
    val accentOnSecondary: Color,
    val error: Color,
    val onError: Color,
    val warning: Color,
    val onWarning: Color,
    val success: Color,
    val onSuccess: Color,
    val info: Color,
    val onInfo: Color,
    val outline: Color,
    val divider: Color,
    val overlay: Color,
)
