package com.canvas.ink.basic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Small read-only status label. /
 * . Non-interactive informational capsule.
 */
@Composable
fun CanvasTag(
    label: String,
    modifier: Modifier = Modifier,
    containerColor: Color? = null,
    contentColor: Color? = null,
) {
    val t = LocalSemanticTokens.current
    val bg = containerColor ?: t.color.bgSurfaceAlt
    val fg = contentColor ?: t.color.textSecondary
    Box(
        modifier = modifier
            .background(bg, RoundedCornerShape(t.radius.sm))
            .padding(horizontal = t.space.xs, vertical = t.space.xxs),
    ) {
        Text(
            text = label,
            style = TextFromType(t.type.labelSmall, weight = FontWeight.Medium),
            color = fg,
            maxLines = 1,
        )
    }
}
