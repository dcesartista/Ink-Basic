package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Single-select radio option. .
 */
@Composable
fun CanvasRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val t = LocalSemanticTokens.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            enabled = enabled,
            colors = RadioButtonDefaults.colors(
                selectedColor = t.color.accentPrimary,
                unselectedColor = t.color.outline,
                disabledSelectedColor = t.color.textDisabled,
                disabledUnselectedColor = t.color.textDisabled,
            ),
        )
        Text(
            text = text,
            style = TextFromType(t.type.body),
            color = if (enabled) t.color.textPrimary else t.color.textDisabled,
            modifier = Modifier.weight(1f),
        )
    }
}
