package com.canvas.ink.basic.token

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
