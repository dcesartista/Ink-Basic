package com.canvas.ink.basic.palette

import com.canvas.ink.basic.token.SemanticTokens

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
         * Strict completeness (ADR-0001) is enforced by the type system:
         * [SemanticTokens] has no optional members, and [Palette] cannot be
         * built without all three mode sets — so a partial palette does not
         * compile. A runtime check here would be dead code.
         *
         * What the compiler *cannot* check is that the values are usable —
         * that every `on*` pair meets contrast, that no colour is accidentally
         * transparent. That is the palette validator's job (ADR-0001), which
         * lives in `PaletteTest` and runs in CI.
         */
        const val CONTRACT = "ADR-0001 four-tier tokens; light + dark + highContrast"
    }
}
