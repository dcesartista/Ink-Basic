package com.canvas.ink.basic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Horizontal separator. Mirrors the standalone `Divider` in the inventory.
 * Decorative only (color.divider is exempt from the contrast validator).
 */
@Composable
fun CanvasDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
) {
    val t = LocalSemanticTokens.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .background(t.color.divider),
    )
}
