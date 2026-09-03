package com.canvas.ink.basic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/** Timeline entry.  nodes. */
data class CanvasTimelineItem(
    val title: String,
    val supportingText: String? = null,
    val timestamp: String? = null,
    val icon: ImageVector? = null,
    val accent: Color? = null,
)

/**
 * Vertical event timeline. . Each [items]
 * entry renders a node (accent-filled with optional icon) joined by a connector
 * line, with title + optional supporting text/timestamp.
 */
@Composable
fun CanvasTimeline(
    items: List<CanvasTimelineItem>,
    modifier: Modifier = Modifier,
) {
    val t = LocalSemanticTokens.current
    val nodeSize = t.sizing.densityCompact
    Column(modifier = modifier) {
        items.forEachIndexed { index, item ->
            Row {
                // Node column: circle + (for all but last) a connector line below.
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val accent = item.accent ?: t.color.accentPrimary
                    Box(
                        modifier = Modifier
                            .size(nodeSize)
                            .clip(CircleShape)
                            .background(accent),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (item.icon != null) {
                            androidx.compose.material3.Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = t.color.accentOnPrimary,
                                modifier = Modifier.size(t.sizing.iconSizeSm),
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(t.space.sm)
                                    .clip(CircleShape)
                                    .background(t.color.accentOnPrimary),
                            )
                        }
                    }
                    if (index < items.lastIndex) {
                        Box(
                            modifier = Modifier
                                .width(t.border.thin)
                                .fillMaxHeight()
                                .background(t.color.divider),
                        )
                    }
                }
                // Content column.
                Column(modifier = Modifier.padding(start = t.space.md, bottom = t.space.lg)) {
                    if (item.timestamp != null) {
                        Text(
                            text = item.timestamp,
                            style = TextFromType(t.type.labelSmall),
                            color = t.color.textTertiary,
                        )
                    }
                    Text(
                        text = item.title,
                        style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                        color = t.color.textPrimary,
                    )
                    if (item.supportingText != null) {
                        Text(
                            text = item.supportingText,
                            style = TextFromType(t.type.bodySmall),
                            color = t.color.textSecondary,
                        )
                    }
                }
            }
        }
    }
}
