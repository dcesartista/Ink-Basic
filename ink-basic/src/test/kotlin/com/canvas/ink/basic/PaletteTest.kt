package com.canvas.ink.basic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.DefaultPalette
import com.canvas.ink.basic.token.SemanticTokens
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure-JVM validation of the palette layer (ADR-0001: strict completeness +
 * the shared on* contrast contract). No instrumentation needed.
 *
 * Every mode is checked against every contrast pair the contract defines — a
 * partial check is what let a malformed `error` color ship with alpha 0.
 */
class PaletteTest {

    /** WCAG 2.2 AA: normal-size text needs 4.5:1 (QUALITY-BAR §5). */
    private val textMinimum = 4.5f

    /** WCAG 2.2 AA 1.4.11: UI-component boundaries need 3:1. */
    private val nonTextMinimum = 3.0f

    private val modes: List<Pair<String, SemanticTokens>> = listOf(
        "light" to DefaultPalette.instance.light,
        "dark" to DefaultPalette.instance.dark,
        "highContrast" to DefaultPalette.instance.highContrast,
    )

    @Test
    fun resolvePicksCorrectModeSet() {
        val p = DefaultPalette.instance
        assertEquals(p.light, p.resolve(darkTheme = false, highContrast = false))
        assertEquals(p.dark, p.resolve(darkTheme = true, highContrast = false))
        assertEquals(p.highContrast, p.resolve(darkTheme = true, highContrast = true))
        assertEquals(p.highContrast, p.resolve(darkTheme = false, highContrast = true))
    }

    /**
     * The shared on* contract (ADR-0001): every accent/state color ships a
     * contrast pair, and the pair must hold in EVERY mode — not just the one
     * that happens to be checked.
     */
    @Test
    fun everyOnPairMeetsWcagInEveryMode() {
        modes.forEach { (mode, t) ->
            val pairs = listOf(
                "accentPrimary/accentOnPrimary" to (t.color.accentPrimary to t.color.accentOnPrimary),
                "accentSecondary/accentOnSecondary" to (t.color.accentSecondary to t.color.accentOnSecondary),
                "error/onError" to (t.color.error to t.color.onError),
                "warning/onWarning" to (t.color.warning to t.color.onWarning),
                "success/onSuccess" to (t.color.success to t.color.onSuccess),
                "info/onInfo" to (t.color.info to t.color.onInfo),
            )
            pairs.forEach { (name, pair) ->
                val (bg, fg) = pair
                assertContrast("$mode: $name", fg, bg, textMinimum)
            }
        }
    }

    /**
     * Body text must be legible on every surface it can land on. textTertiary
     * is spec'd as "muted / placeholder" — placeholder text is still text, so
     * it carries the full 4.5:1 floor. textDisabled is exempt (WCAG 1.4.3
     * excludes inactive controls).
     */
    @Test
    fun bodyTextMeetsWcagOnEverySurface() {
        modes.forEach { (mode, t) ->
            val texts = listOf(
                "textPrimary" to t.color.textPrimary,
                "textSecondary" to t.color.textSecondary,
                "textTertiary" to t.color.textTertiary,
            )
            val surfaces = listOf(
                "bgSurface" to t.color.bgSurface,
                "bgSurfaceAlt" to t.color.bgSurfaceAlt,
                "bgSurfaceRaised" to t.color.bgSurfaceRaised,
            )
            texts.forEach { (tName, fg) ->
                surfaces.forEach { (sName, bg) ->
                    assertContrast("$mode: $tName on $sName", fg, bg, textMinimum)
                }
            }
        }
    }

    /** Outline contrast: bounds interactive controls (3:1). */
    @Test
    fun outlineMeetsNonTextContrast() {
        modes.forEach { (mode, t) ->
            assertContrast("$mode: outline on bgSurface", t.color.outline, t.color.bgSurface, nonTextMinimum)
        }
    }

    /**
     * Spacing scale contract (8-point grid plus the extended sub-steps). Locks
     * the exact contract values so a later palette cannot silently drift them.
     */
    @Test
    fun spacingMatchesReconciledScale() {
        modes.forEach { (mode, t) ->
            val s = t.space
            assertEquals("$mode: xxxs", 2.dp, s.xxxs)
            assertEquals("$mode: xxs", 4.dp, s.xxs)
            assertEquals("$mode: xxs2", 6.dp, s.xxs2)
            assertEquals("$mode: xs", 8.dp, s.xs)
            assertEquals("$mode: sm", 12.dp, s.sm)
            assertEquals("$mode: md", 16.dp, s.md)
            assertEquals("$mode: sm2", 20.dp, s.sm2)
            assertEquals("$mode: lg", 24.dp, s.lg)
            assertEquals("$mode: xl", 32.dp, s.xl)
            assertEquals("$mode: xxxl", 40.dp, s.xxxl)
            assertEquals("$mode: xxl", 48.dp, s.xxl)
            assertEquals("$mode: xxxxl", 80.dp, s.xxxxl)
        }
    }

    /** Every color token must be fully opaque — a malformed ARGB literal
     *  (e.g. `Color(0xFFC00)`) silently yields alpha 0 and renders nothing.
     *  `overlay` is the one intentional exception: it is a scrim. */
    @Test
    fun everyColorTokenIsOpaque() {
        modes.forEach { (mode, t) ->
            val c = t.color
            listOf(
                "bgSurface" to c.bgSurface, "bgSurfaceAlt" to c.bgSurfaceAlt,
                "bgSurfaceRaised" to c.bgSurfaceRaised, "textPrimary" to c.textPrimary,
                "textSecondary" to c.textSecondary, "textTertiary" to c.textTertiary,
                "textDisabled" to c.textDisabled, "textInverse" to c.textInverse,
                "accentPrimary" to c.accentPrimary, "accentOnPrimary" to c.accentOnPrimary,
                "accentSecondary" to c.accentSecondary, "accentOnSecondary" to c.accentOnSecondary,
                "error" to c.error, "onError" to c.onError,
                "warning" to c.warning, "onWarning" to c.onWarning,
                "success" to c.success, "onSuccess" to c.onSuccess,
                "info" to c.info, "onInfo" to c.onInfo,
                "outline" to c.outline, "divider" to c.divider,
            ).forEach { (name, color) ->
                assertEquals("$mode: $name is not opaque (malformed ARGB literal?)", 1f, color.alpha, 0.001f)
            }
        }
    }

    /**
     * Scale ordering per mode. Field completeness is already guaranteed by the
     * compiler (SemanticTokens has no optional members and no defaults), so
     * this asserts the orderings the type system cannot.
     */
    @Test
    fun scalesAreMonotonicPerMode() {
        modes.forEach { (mode, t) ->
            assertTrue("$mode radius", t.radius.none <= t.radius.sm)
            assertTrue("$mode radius", t.radius.sm <= t.radius.md)
            assertTrue("$mode radius", t.radius.md <= t.radius.lg)
            assertTrue("$mode radius", t.radius.lg <= t.radius.pill)
            assertTrue("$mode elevation", t.elevation.flat <= t.elevation.sm)
            assertTrue("$mode elevation", t.elevation.sm <= t.elevation.md)
            assertTrue("$mode elevation", t.elevation.md <= t.elevation.lg)
            assertTrue("$mode space", t.space.xxs <= t.space.xs)
            assertTrue("$mode space", t.space.xs <= t.space.sm)
            assertTrue("$mode space", t.space.sm <= t.space.md)
            assertTrue("$mode space", t.space.md <= t.space.lg)
            assertTrue("$mode space", t.space.lg <= t.space.xl)
            assertTrue("$mode space", t.space.xl <= t.space.xxl)
            // Extended sub-steps keep the scale strict:
            // 2 <= 4 <= 6 <= 8 and 20 <= 24 <= 32 <= 40 <= 48 <= 80.
            assertTrue("$mode space", t.space.xxxs <= t.space.xxs)
            assertTrue("$mode space", t.space.xxs <= t.space.xxs2)
            assertTrue("$mode space", t.space.xxs2 <= t.space.xs)
            assertTrue("$mode space", t.space.sm <= t.space.sm2)
            assertTrue("$mode space", t.space.sm2 <= t.space.lg)
            assertTrue("$mode space", t.space.xl <= t.space.xxxl)
            assertTrue("$mode space", t.space.xxxl <= t.space.xxl)
            assertTrue("$mode space", t.space.xxl <= t.space.xxxxl)
            assertTrue("$mode type", t.type.caption.size.value <= t.type.body.size.value)
            assertTrue("$mode type", t.type.body.size.value <= t.type.h1.size.value)
            assertTrue("$mode type", t.type.h1.size.value <= t.type.display.size.value)
            assertTrue("$mode motion", t.motion.durationFast <= t.motion.durationNormal)
            assertTrue("$mode motion", t.motion.durationNormal <= t.motion.durationSlow)
            // Border strokes: thin <= medium <= thick.
            assertTrue("$mode border", t.border.thin <= t.border.medium)
            assertTrue("$mode border", t.border.medium <= t.border.thick)
            // 48dp minimum touch target is core-pinned, not themeable (QUALITY-BAR §5).
            assertTrue("$mode touchTarget", t.sizing.touchTarget >= 48.dp)
        }
    }

    private fun assertContrast(label: String, fg: Color, bg: Color, minimum: Float) {
        val ratio = contrastRatio(fg, bg)
        assertTrue(
            "$label contrast %.2f:1 is below the %.1f:1 floor".format(ratio, minimum),
            ratio >= minimum,
        )
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
            else ((v + 0.055f) / 1.055f).toDouble().pow(2.4).toFloat()
        return 0.2126f * linear(c.red) + 0.7152f * linear(c.green) + 0.0722f * linear(c.blue)
    }
}
