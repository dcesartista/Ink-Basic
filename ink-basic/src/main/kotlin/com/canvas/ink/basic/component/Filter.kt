package com.canvas.ink.basic.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Horizontal, horizontally-scrollable row of filter chips. Mirrors 
 * . Single or multi select is expressed entirely by the caller's
 * [selected]/[onToggle] contract — this component just renders each option as a
 * chip that toggles to the filled accent style when selected.
 */
@Composable
fun <T> CanvasFilter(
    options: List<T>,
    label: (T) -> String,
    selected: List<T>,
    onToggle: (T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val t = LocalSemanticTokens.current
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option in selected
            CanvasChip(
                label = label(option),
                selected = isSelected,
                style = if (isSelected) CanvasChipStyle.Fill else CanvasChipStyle.Outline,
                enabled = enabled,
                onClick = {
                    onToggle(option)
                },
            )
            if (index < options.lastIndex) {
                Spacer(Modifier.width(t.space.xs))
            }
        }
    }
}
