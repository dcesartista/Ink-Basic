package com.canvas.ink.basic.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Single-select dropdown. . Displays the current
 * selection in an outlined field; tapping expands the option list.
 */
@Composable
fun <T> CanvasSelect(
    options: List<T>,
    label: (T) -> String,
    modifier: Modifier = Modifier,
    selected: T? = null,
    onSelect: (T) -> Unit = {},
    placeholder: String? = null,
    enabled: Boolean = true,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        Surface(
            onClick = { if (enabled) expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = t.sizing.touchTarget),
            shape = RoundedCornerShape(t.radius.md),
            color = if (enabled) t.color.bgSurface else t.color.bgSurfaceAlt,
            border = androidx.compose.foundation.BorderStroke(t.border.thin, t.color.outline),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = t.space.md, vertical = t.space.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selected?.let(label) ?: placeholder ?: "",
                    style = TextFromType(t.type.body),
                    color = if (selected != null) t.color.textPrimary else t.color.textTertiary,
                    modifier = Modifier.weight(1f),
                )
                if (trailingIcon != null) {
                    Spacer(Modifier.width(t.space.sm))
                    trailingIcon()
                }
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                val isSelected = option == selected
                DropdownMenuItem(
                    text = {
                        Text(
                            text = label(option),
                            style = TextFromType(t.type.body, weight = if (isSelected) FontWeight.SemiBold else null),
                            color = if (isSelected) t.color.accentPrimary else t.color.textPrimary,
                        )
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                )
            }
        }
    }
}
