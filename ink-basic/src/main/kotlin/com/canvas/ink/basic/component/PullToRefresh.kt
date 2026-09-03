package com.canvas.ink.basic.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Pull-to-refresh container. . Wraps
 * [content] in a scrollable surface that triggers [onRefresh] on overscroll
 * while [isRefreshing]. Uses the default indicator, which is already token-wired
 * to the accent color via the CanvasTheme T2 bridge (M3 `primary`).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasPullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
    ) {
        content()
    }
}
