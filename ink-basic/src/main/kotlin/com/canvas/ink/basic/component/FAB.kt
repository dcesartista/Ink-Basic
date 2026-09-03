package com.canvas.ink.basic.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Floating action button. Icon-only via [icon]; extended (icon + label) via
 * [icon] and [label].
 */
@Composable
fun CanvasFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String? = null,
) {
    val t = LocalSemanticTokens.current
    val shape = RoundedCornerShape(t.radius.pill)
    if (label != null && icon != null) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            containerColor = t.color.accentPrimary,
            contentColor = t.color.accentOnPrimary,
            shape = shape,
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = t.color.accentOnPrimary)
            Text(
                text = label,
                style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                color = t.color.accentOnPrimary,
            )
        }
    } else {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            containerColor = t.color.accentPrimary,
            contentColor = t.color.accentOnPrimary,
            shape = shape,
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, tint = t.color.accentOnPrimary)
            }
        }
    }
}
