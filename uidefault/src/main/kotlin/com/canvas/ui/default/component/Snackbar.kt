package com.canvas.ui.default.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ui.default.palette.LocalSemanticTokens

/**
 * Transient message. Uses inverse text on a raised surface so it reads in any
 * theme. (Animate in/out with motion.duration tokens in the host).
 */
@Composable
fun CanvasSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(t.radius.md),
        color = t.color.bgSurfaceRaised,
        tonalElevation = t.elevation.md,
        shadowElevation = t.elevation.md,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = t.space.md, vertical = t.space.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = message,
                modifier = Modifier.weight(1f),
                style = TextFromType(t.type.body),
                color = t.color.textInverse,
            )
            if (actionLabel != null && onAction != null) {
                Spacer(Modifier.width(t.space.md))
                Text(
                    text = actionLabel,
                    style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                    color = t.color.accentPrimary,
                    modifier = Modifier.clickable(onClick = onAction),
                )
            }
        }
    }
}
