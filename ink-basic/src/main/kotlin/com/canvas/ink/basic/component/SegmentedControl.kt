package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Single-select segmented control. `SingleChoiceSegmentedButtonRow` handles the
 * contiguous framing; each segment is a [SegmentedButton].
 */
@Composable
fun CanvasSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val t = LocalSemanticTokens.current
    SingleChoiceSegmentedButtonRow(modifier = modifier.fillMaxWidth()) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                selected = index == selectedIndex,
                onClick = { onSelect(index) },
                enabled = enabled,
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = t.color.bgSurfaceAlt,
                    activeContentColor = t.color.accentPrimary,
                    inactiveContainerColor = t.color.bgSurface,
                    inactiveContentColor = t.color.textSecondary,
                ),
            ) {
                Text(
                    text = option,
                    style = TextFromType(t.type.label, weight = if (index == selectedIndex) FontWeight.SemiBold else FontWeight.Medium),
                    maxLines = 1,
                )
            }
        }
    }
}
