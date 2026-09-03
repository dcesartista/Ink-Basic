package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * The scrolling body of a list screen.
 *
 * [key] is **required, not defaulted**. An unkeyed lazy list re-composes and loses scroll
 * position on every reorder — a QUALITY-BAR §5 performance defect that is invisible until
 * the data changes. Making it a mandatory parameter means a caller cannot omit it by
 * accident, only by deliberately passing something.
 *
 * Item rhythm is `space.layout.item`; the caller supplies [contentPadding], normally the
 * padding handed down by [CanvasScreenScaffold].
 */
@Composable
fun <T> CanvasListBody(
    items: List<T>,
    key: (T) -> Any,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    itemSpacing: androidx.compose.ui.unit.Dp = LocalSemanticTokens.current.space.layout.item,
    header: (@Composable LazyItemScope.() -> Unit)? = null,
    footer: (@Composable LazyItemScope.() -> Unit)? = null,
    itemContent: @Composable LazyItemScope.(T) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(itemSpacing),
    ) {
        if (header != null) {
            item(key = "canvas-list-header", content = header)
        }
        items(items = items, key = key) { item ->
            itemContent(item)
        }
        if (footer != null) {
            item(key = "canvas-list-footer", content = footer)
        }
    }
}
