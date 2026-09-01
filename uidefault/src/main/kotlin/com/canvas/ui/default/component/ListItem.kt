package com.canvas.ui.default.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.canvas.ui.default.palette.LocalSemanticTokens

/**
 * A selectable row: title + optional supporting line and trailing slot.
 */
@Composable
fun CanvasListItem(
    title: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current
    val base = modifier
        .fillMaxWidth()
        .heightIn(min = t.sizing.densityComfortable)
        .padding(horizontal = t.space.layout.item, vertical = t.space.sm)
    Row(
        modifier = base.then(
            if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leading != null) {
            leading()
            Spacer(Modifier.width(t.space.md))
        }
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, color = t.color.textPrimary)
            if (supportingText != null) {
                Text(
                    supportingText,
                    style = MaterialTheme.typography.bodySmall,
                    color = t.color.textSecondary,
                )
            }
        }
        if (trailing != null) {
            Spacer(Modifier.width(t.space.sm))
            trailing()
        }
    }
}
