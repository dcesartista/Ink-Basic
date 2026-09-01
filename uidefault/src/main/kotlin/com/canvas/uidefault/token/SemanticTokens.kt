package com.canvas.uidefault.token

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * T1 primitive — raw color value. A palette resolves semantic tokens onto
 * these; components never read primitives directly.
 */
data class RawColor(val value: Color)

/**
 * T3 — Semantics. The contract.
 *
 * A complete palette = a full [SemanticTokens] for each of
 * [SemanticTokens.light] / [SemanticTokens.dark] / [SemanticTokens.highContrast]
 * (ADR-0001: strict completeness in every mode).
 *
 * Components consume ONLY these semantic tokens. They never reference
 * primitives, raw hex, or Dp values directly.
 */
data class SemanticTokens(
    val color: ColorTokens,
    val type: TypeTokens,
    val space: SpaceTokens,
    val radius: RadiusTokens,
    val elevation: ElevationTokens,
    val motion: MotionTokens,
    val sizing: SizingTokens,
)
