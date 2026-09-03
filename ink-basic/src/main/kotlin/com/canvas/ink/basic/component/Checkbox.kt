package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Selection checkbox with a label. Tri-state via [checked] null for
 * indeterminate, with [size] and [enabled] variants.
 */
@Composable
fun CanvasCheckbox(
    text: String,
    checked: Boolean?,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 20.dp,
) {
    val t = LocalSemanticTokens.current
    Checkbox(
        checked = checked ?: false,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        modifier = modifier.size(size),
        colors = CheckboxDefaults.colors(
            checkedColor = t.color.accentPrimary,
            checkmarkColor = t.color.accentOnPrimary,
            uncheckedColor = t.color.outline,
            disabledCheckedColor = t.color.bgSurfaceAlt,
            disabledUncheckedColor = t.color.bgSurfaceAlt,
            disabledIndeterminateColor = t.color.bgSurfaceAlt,
        ),
    )
}
