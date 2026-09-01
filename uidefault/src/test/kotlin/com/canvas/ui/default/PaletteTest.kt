package com.canvas.ui.default

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.canvas.ui.default.palette.DefaultPalette
import com.canvas.ui.default.token.SemanticTokens
import kotlin.math.max
import kotlin.math.min
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure-JVM validation of the palette layer (ADR-0001: strict completeness +
 * the shared on* contrast contract). No instrumentation needed.
 */
class PaletteTest {

    @Test
    fun resolvePicksCorrectModeSet() {
        val p = DefaultPalette.instance
        assertEquals(p.light, p.resolve(darkTheme = false, highContrast = false))
        assertEquals(p.dark, p.resolve(darkTheme = true, highContrast = false))
        assertEquals(p.highContrast, p.resolve(darkTheme = true, highContrast = true))
        assertEquals(p.highContrast, p.resolve(darkTheme = false, highContrast = true))
    }

    @Test
    fun paletteIsCompletePerMode() {
        listOf(
            DefaultPalette.instance.light,
            DefaultPalette.instance.dark,
            DefaultPalette.instance.highContrast,
        ).forEach { t ->
            assertCompleteness(t)
        }
    }

    @Test
    fun accentContrastPairsMeetWcag() {
        val p = DefaultPalette.instance
        listOf(
            Triple(p.light.color.accentPrimary, p.light.color.accentOnPrimary, "light"),
            Triple(p.dark.color.accentPrimary, p.dark.color.accentOnPrimary, "dark"),
            Triple(p.highContrast.color.accentPrimary, p.highContrast.color.accentOnPrimary, "hc"),
        ).forEach { (fg, bg, mode) ->
            assertTrue(
                "accent pair fails contrast in $mode",
                contrastRatio(fg, bg) >= 4.5f,
            )
        }
    }

    @Test
    fun textOnSurfaceMeetsWcag() {
        val p = DefaultPalette.instance
        p.also { pt ->
            listOf(pt.light, pt.dark, pt.highContrast).forEach { t ->
                assertTrue(
                    "primary text fails contrast",
                    contrastRatio(t.color.textPrimary, t.color.bgSurface) >= 4.5f,
                )
            }
        }
    }

    private fun assertCompleteness(t: SemanticTokens) {
        // Presence of every color field (compiler already enforces exactness;
        // here we assert the trusted values are distinct/non-null in practice).
        assertTrue(t.color.accentPrimary != t.color.bgSurface)
        assertTrue(t.color.textPrimary != t.color.textSecondary)
        assertTrue(t.radius.sm <= t.radius.md)
        assertTrue(t.radius.md <= t.radius.lg)
        assertTrue(t.elevation.sm <= t.elevation.md)
        assertTrue(t.sizing.touchTarget >= 48.dp) // 48dp minimum touch target (QUALITY-BAR)
    }

    private fun contrastRatio(a: Color, b: Color): Float {
        val la = luminance(a)
        val lb = luminance(b)
        val hi = max(la, lb)
        val lo = min(la, lb)
        return (hi + 0.05f) / (lo + 0.05f)
    }

    private fun luminance(c: Color): Float {
        fun linear(v: Float): Float =
            if (v <= 0.03928f) v / 12.92f
            else ((v + 0.055f) / 1.055f).pow(2.4f)
        val r = linear(c.red)
        val g = linear(c.green)
        val b = linear(c.blue)
        return 0.2126f * r + 0.7152f * g + 0.0722f * b
    }
}

private fun Float.pow(exp: Int): Float = Math.pow(this.toDouble(), exp.toDouble()).toFloat()
