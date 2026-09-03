package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * On/off switch control. Optional [label] is rendered to the side (so a screen
 * can pair it without a custom layout).
 */
@Composable
fun CanvasToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
) {
    val t = LocalSemanticTokens.current
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (label != null) {
            Text(
                text = label,
                style = TextFromType(t.type.body, weight = null),
                color = if (enabled) t.color.textPrimary else t.color.textDisabled,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(t.space.md))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = t.color.accentOnPrimary,
                checkedTrackColor = t.color.accentPrimary,
                uncheckedThumbColor = t.color.textInverse,
                uncheckedTrackColor = t.color.bgSurfaceAlt,
                uncheckedBorderColor = t.color.outline,
                disabledCheckedThumbColor = t.color.textDisabled,
                disabledCheckedTrackColor = t.color.bgSurfaceAlt,
                disabledUncheckedThumbColor = t.color.textDisabled,
                disabledUncheckedTrackColor = t.color.bgSurfaceAlt,
                disabledUncheckedBorderColor = t.color.textDisabled,
            ),
        )
    }
}
