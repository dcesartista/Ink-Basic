package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Single-line helper bubble. .
 * Renders an inverted-surface capsule with explanatory text. Content overrides
 * color via the token pair (textInverse on bgSurfaceRaised would lack contrast
 * in dark mode, so we use a solid accent-backed surface here by default).
 */
@Composable
fun CanvasTooltip(
    text: String,
    modifier: Modifier = Modifier,
) {
    val t = LocalSemanticTokens.current
    val bg = t.color.textPrimary
    val fg = t.color.textInverse
    Surface(
        modifier = modifier.heightIn(min = t.sizing.densityCompact),
        shape = RoundedCornerShape(t.radius.sm),
        color = bg,
    ) {
        Box(
            modifier = Modifier.padding(horizontal = t.space.sm, vertical = t.space.xs),
        ) {
            Text(
                text = text,
                style = TextFromType(t.type.labelSmall),
                color = fg,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}
