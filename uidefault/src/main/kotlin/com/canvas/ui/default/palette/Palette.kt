package com.canvas.ui.default.palette

import com.canvas.ui.default.token.SemanticTokens

/**
 * A "look" = a complete palette for each display mode (ADR-0001: strict
 * completeness — a theme is not valid until all three modes resolve).
 *
 * A palette NEVER carries components; it only supplies T3 semantic token
 * values against the shared component contract. Swapping the palette swaps
 * the entire look without touching a single component.
 */
data class Palette(
    val id: String,
    val light: SemanticTokens,
    val dark: SemanticTokens,
    val highContrast: SemanticTokens,
) {
    /** Returns the complete set for a boolean dark + high-contrast resolution. */
    fun resolve(darkTheme: Boolean, highContrast: Boolean): SemanticTokens =
        when {
            highContrast -> this.highContrast
            darkTheme -> this.dark
            else -> this.light
        }

    companion object {
        /**
         * Strict completeness is enforced by the type system: you cannot build a
         * [SemanticTokens] without every token, nor a [Palette] without all three
         * mode sets — the compiler rejects partial palettes. This helper exists
         * for runtime/external (e.g. JSON-loaded) palettes; for code-built
         * palettes the non-null types already guarantee completeness.
         */
        @JvmStatic
        fun requireComplete(p: Palette) {
            require(p.light != null) { "palette '${p.id}' missing light set" }
            require(p.dark != null) { "palette '${p.id}' missing dark set" }
            require(p.highContrast != null) { "palette '${p.id}' missing highContrast set" }
        }
    }
}
