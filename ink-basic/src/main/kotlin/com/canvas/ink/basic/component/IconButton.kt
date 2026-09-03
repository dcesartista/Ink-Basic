package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Icon-only action button. Touch target pinned to size.touchTarget (core
 * correctness, ADR-0001). .
 */
@Composable
fun CanvasIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true,
    tint: Color? = null,
) {
    val t = LocalSemanticTokens.current
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.size(t.sizing.touchTarget),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint ?: if (enabled) t.color.textSecondary else t.color.textDisabled,
            modifier = Modifier.size(t.sizing.iconSize),
        )
    }
}
