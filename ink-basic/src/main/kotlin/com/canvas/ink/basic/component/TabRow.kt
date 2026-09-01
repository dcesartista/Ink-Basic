package com.canvas.ink.basic.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Selection tab row. Active tab uses the accent with an accent underline;
 * inactive uses text secondary. Each tab is a full touch target.
 */
@Composable
fun CanvasTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val t = LocalSemanticTokens.current
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            tabs.forEachIndexed { index, title ->
                val selected = index == selectedIndex
                Text(
                    text = title,
                    style = TextFromType(
                        t.type.label,
                        weight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                    ),
                    color = if (selected) t.color.accentPrimary else t.color.textSecondary,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = t.sizing.touchTarget)
                        .padding(vertical = t.space.sm)
                        .clickable { onSelect(index) },
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = t.color.divider)
    }
}
