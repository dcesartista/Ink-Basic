package com.canvas.ink.basic.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/** Chip visual variant —  styles. */
enum class CanvasChipStyle { Fill, Outline, Duotone }

/**
 * Compact selectable/filter chip.  (fill/outline/
 * duotone styles).
 */
@Composable
fun CanvasChip(
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    selected: Boolean = false,
    style: CanvasChipStyle = CanvasChipStyle.Fill,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
) {
    val t = LocalSemanticTokens.current
    val shape = RoundedCornerShape(t.radius.pill)
    val interaction = remember { MutableInteractionSource() }

    val container: Color
    val content: Color
    val border: Color?
    when (style) {
        CanvasChipStyle.Fill -> {
            container = if (selected) t.color.accentPrimary else t.color.bgSurfaceAlt
            content = if (selected) t.color.accentOnPrimary else t.color.textPrimary
            border = null
        }
        CanvasChipStyle.Outline -> {
            container = if (selected) t.color.accentPrimary else t.color.bgSurface
            content = if (selected) t.color.accentOnPrimary else t.color.textPrimary
            border = if (selected) t.color.accentPrimary else t.color.outline
        }
        CanvasChipStyle.Duotone -> {
            container = t.color.bgSurfaceAlt
            content = if (selected) t.color.accentPrimary else t.color.textSecondary
            border = if (selected) t.color.accentPrimary else null
        }
    }

    Surface(
        onClick = onClick ?: {},
        enabled = enabled && onClick != null,
        modifier = modifier.heightIn(min = t.sizing.densityCompact),
        shape = shape,
        color = container,
        contentColor = content,
        border = border?.let { BorderStroke(t.border.thin, it) },
        interactionSource = interaction,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = t.space.md, vertical = t.space.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leading != null) {
                leading()
                Spacer(Modifier.width(t.space.xs))
            }
            Text(
                text = label,
                style = TextFromType(t.type.label, weight = if (selected) FontWeight.SemiBold else FontWeight.Medium),
                maxLines = 1,
            )
            if (trailing != null) {
                Spacer(Modifier.width(t.space.xs))
                trailing()
            }
        }
    }
}
