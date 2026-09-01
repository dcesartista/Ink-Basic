package com.canvas.ink.basic.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * A single bottom-nav destination definition.
 */
data class NavDest(val label: String, val icon: ImageVector, val selectedIcon: ImageVector? = null)

/**
 * Bottom navigation bar. Selected uses the accent; unselected uses text
 * secondary. Each item is a full touch target.
 */
@Composable
fun CanvasBottomNav(
    destinations: List<NavDest>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val t = LocalSemanticTokens.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = t.space.layout.page, vertical = t.space.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        destinations.forEachIndexed { index, dest ->
            BottomNavItem(
                dest = dest,
                selected = index == selectedIndex,
                onClick = { onSelect(index) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    dest: NavDest,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val t = LocalSemanticTokens.current
    val color = if (selected) t.color.accentPrimary else t.color.textSecondary
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = t.space.xs),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = if (selected) dest.selectedIcon ?: dest.icon else dest.icon,
            contentDescription = dest.label,
            tint = color,
        )
        Text(
            text = dest.label,
            style = TextFromType(
                t.type.labelSmall,
                weight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            ),
            color = color,
            maxLines = 1,
        )
    }
}
