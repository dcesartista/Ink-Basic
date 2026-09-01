package com.canvas.ink.basic.palette

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.token.SemanticTokens

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
    val m3Scheme = if (darkTheme) {
        darkColorScheme(
            primary = tokens.color.accentPrimary,
            onPrimary = tokens.color.accentOnPrimary,
            secondary = tokens.color.accentSecondary,
            onSecondary = tokens.color.accentOnSecondary,
            surface = tokens.color.bgSurface,
            onSurface = tokens.color.textPrimary,
            error = tokens.color.error,
            onError = tokens.color.onError,
            outline = tokens.color.outline,
        )
    } else {
        lightColorScheme(
            primary = tokens.color.accentPrimary,
            onPrimary = tokens.color.accentOnPrimary,
            secondary = tokens.color.accentSecondary,
            onSecondary = tokens.color.accentOnSecondary,
            surface = tokens.color.bgSurface,
            onSurface = tokens.color.textPrimary,
            error = tokens.color.error,
            onError = tokens.color.onError,
            outline = tokens.color.outline,
        )
    }
    val m3Typography = Typography(
        displayLarge = ts(tokens.type.display),
        headlineSmall = ts(tokens.type.h3),
        titleMedium = ts(tokens.type.h4),
        titleLarge = ts(tokens.type.h2),
        bodyLarge = ts(tokens.type.body),
        bodyMedium = ts(tokens.type.body),
        bodySmall = ts(tokens.type.bodySmall),
        labelLarge = ts(tokens.type.label),
        labelMedium = ts(tokens.type.label),
        labelSmall = ts(tokens.type.labelSmall),
    )

    CompositionLocalProvider(LocalSemanticTokens provides tokens) {
        MaterialTheme(
            colorScheme = m3Scheme,
            typography = m3Typography,
            content = content,
        )
    }
}

private fun ts(style: com.canvas.ink.basic.token.TypeStyle): TextStyle = TextStyle(
    fontSize = style.size,
    fontWeight = FontWeight(style.weight),
    lineHeight = style.lineHeight,
    letterSpacing = style.letterSpacing,
)
