package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * The scrolling body of a form screen.
 *
 * Scrolls, applies `imePadding()` so the focused field is not covered by the soft keyboard,
 * and spaces fields at `space.layout.item`. The keyboard inset is the part hand-rolled
 * forms consistently forgot — it only shows up on a short screen with a bottom field.
 */
@Composable
fun CanvasFormBody(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    fieldSpacing: androidx.compose.ui.unit.Dp = LocalSemanticTokens.current.space.layout.item,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(fieldSpacing),
        content = content,
    )
}
