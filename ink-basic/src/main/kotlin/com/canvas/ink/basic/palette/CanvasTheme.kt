package com.canvas.ink.basic.palette

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import com.canvas.ink.basic.token.SemanticTokens
import com.canvas.ink.basic.token.TypeStyle
import com.canvas.ink.basic.token.toTextStyle

/**
 * CompositionLocal carrying the active [SemanticTokens] for the current mode.
 * Components read tokens from here — never raw colors/Dp (ADR-0001: components
 * consume T3 semantic tokens only).
 */
val LocalSemanticTokens = staticCompositionLocalOf<SemanticTokens> {
    error("CanvasTheme has not provided LocalSemanticTokens")
}

/**
 * Applies a [palette] for the current dark/contrast mode and exposes its
 * [SemanticTokens] via [LocalSemanticTokens]. The Material 3 bridge (T2) maps
 * semantic tokens onto an M3 color scheme + typography underneath so components
 * may lean on M3 primitives. Consumers override [palette] to rebrand.
 */
@Composable
fun CanvasTheme(
    palette: Palette = DefaultPalette.instance,
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrast: Boolean = false,
    content: @Composable () -> Unit,
) {
    val tokens = palette.resolve(darkTheme, highContrast)
    val m3Scheme = m3SchemeFrom(tokens, darkTheme)
    val m3Typography = m3TypographyFrom(tokens)

    CompositionLocalProvider(LocalSemanticTokens provides tokens) {
        MaterialTheme(
            colorScheme = m3Scheme,
            typography = m3Typography,
            content = content,
        )
    }
}


/**
 * T2 — the Material 3 bridge. **Total, not partial** (ADR-0001): every role M3
 * exposes is mapped from a T3 token, so a stray stock widget is visually
 * indistinguishable rather than merely tolerated. A role left unmapped falls
 * back to M3's baseline palette, which is exactly the stock look the token
 * system exists to escape.
 *
 * Deliberately stock: none. Every role below is palette-derived.
 */
private fun m3SchemeFrom(t: SemanticTokens, darkTheme: Boolean): ColorScheme {
    val c = t.color
    val base = if (darkTheme) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = c.accentPrimary,
        onPrimary = c.accentOnPrimary,
        primaryContainer = c.bgSurfaceAlt,
        onPrimaryContainer = c.textPrimary,
        inversePrimary = c.accentSecondary,

        secondary = c.accentSecondary,
        onSecondary = c.accentOnSecondary,
        secondaryContainer = c.bgSurfaceAlt,
        onSecondaryContainer = c.textPrimary,

        tertiary = c.info,
        onTertiary = c.onInfo,
        tertiaryContainer = c.bgSurfaceAlt,
        onTertiaryContainer = c.textPrimary,

        background = c.bgSurface,
        onBackground = c.textPrimary,
        surface = c.bgSurface,
        onSurface = c.textPrimary,
        surfaceVariant = c.bgSurfaceAlt,
        onSurfaceVariant = c.textSecondary,
        surfaceTint = c.accentPrimary,
        surfaceBright = c.bgSurfaceRaised,
        surfaceDim = c.bgSurfaceAlt,
        surfaceContainer = c.bgSurfaceRaised,
        surfaceContainerHigh = c.bgSurfaceRaised,
        surfaceContainerHighest = c.bgSurfaceRaised,
        surfaceContainerLow = c.bgSurfaceAlt,
        surfaceContainerLowest = c.bgSurface,

        inverseSurface = c.textPrimary,
        inverseOnSurface = c.textInverse,

        error = c.error,
        onError = c.onError,
        errorContainer = c.bgSurfaceAlt,
        onErrorContainer = c.error,

        outline = c.outline,
        outlineVariant = c.divider,
        scrim = c.overlay,
    )
}

/** T2 — type bridge: M3's type roles resolved from the T3 scale. */
private fun m3TypographyFrom(t: SemanticTokens): Typography = Typography(
    displayLarge = ts(t.type.display),
    displayMedium = ts(t.type.display),
    displaySmall = ts(t.type.h1),
    headlineLarge = ts(t.type.h1),
    headlineMedium = ts(t.type.h2),
    headlineSmall = ts(t.type.h3),
    titleLarge = ts(t.type.h2),
    titleMedium = ts(t.type.h4),
    titleSmall = ts(t.type.label),
    bodyLarge = ts(t.type.body),
    bodyMedium = ts(t.type.body),
    bodySmall = ts(t.type.bodySmall),
    labelLarge = ts(t.type.label),
    labelMedium = ts(t.type.label),
    labelSmall = ts(t.type.labelSmall),
)

private fun ts(style: TypeStyle): TextStyle = style.toTextStyle()
