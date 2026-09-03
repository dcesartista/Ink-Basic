package com.canvas.ink.basic.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.canvas.ink.basic.palette.LocalSemanticTokens

/** Banner tone —  variants. */
enum class CanvasBannerTone { Info, Success, Warning, Error }

/**
 * Inline informational banner.  (4 variants).
 * Uses the corresponding state color as a tinted accent on a raised surface.
 */
@Composable
fun CanvasBanner(
    message: String,
    modifier: Modifier = Modifier,
    tone: CanvasBannerTone = CanvasBannerTone.Info,
    icon: ImageVector? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    dismissLabel: String? = null,
    onDismiss: (() -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current
    val accent: Color
    when (tone) {
        CanvasBannerTone.Info -> accent = t.color.info
        CanvasBannerTone.Success -> accent = t.color.success
        CanvasBannerTone.Warning -> accent = t.color.warning
        CanvasBannerTone.Error -> accent = t.color.error
    }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(t.radius.md),
        color = t.color.bgSurfaceAlt,
        contentColor = t.color.textPrimary,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = t.space.md, vertical = t.space.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(t.sizing.iconSize),
                )
                Spacer(Modifier.width(t.space.sm))
            }
            Text(
                text = message,
                modifier = Modifier.weight(1f),
                style = TextFromType(t.type.body),
                color = t.color.textPrimary,
            )
            if (actionLabel != null && onAction != null) {
                Spacer(Modifier.width(t.space.sm))
                Text(
                    text = actionLabel,
                    style = TextFromType(t.type.label),
                    color = accent,
                    modifier = Modifier
                        .clickable(onClick = onAction)
                        .padding(vertical = t.space.xxs),
                )
            }
            if (dismissLabel != null && onDismiss != null) {
                Spacer(Modifier.width(t.space.sm))
                Text(
                    text = dismissLabel,
                    style = TextFromType(t.type.label),
                    color = t.color.textSecondary,
                    modifier = Modifier
                        .clickable(onClick = onDismiss)
                        .padding(vertical = t.space.xxs),
                )
            }
        }
    }
}
