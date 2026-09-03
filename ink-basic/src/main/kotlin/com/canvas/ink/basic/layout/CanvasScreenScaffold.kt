package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * The page frame every Canvas screen sits in.
 *
 * Owns the three decisions that were being made independently — and inconsistently — by
 * every screen: where the top region goes, where a pinned bottom region goes, and how much
 * horizontal page padding the body gets. Page padding resolves to `space.layout.page`,
 * the token defined for it in ADR-0001 and which no hand-rolled screen was using.
 *
 * The [content] lambda receives the padding it must apply. A scrolling body should pass it
 * to the scroll container's `contentPadding` rather than to an outer `Modifier.padding`,
 * so content scrolls under the bars instead of being clipped by them.
 *
 * @param pagePadding horizontal inset for the body. Pass `0.dp` for edge-to-edge bodies
 *   (a full-bleed media header, a list whose rows draw their own inset).
 */
@Composable
fun CanvasScreenScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingAction: @Composable () -> Unit = {},
    pagePadding: androidx.compose.ui.unit.Dp = LocalSemanticTokens.current.space.layout.page,
    content: @Composable (PaddingValues) -> Unit,
) {
    val t = LocalSemanticTokens.current
    val direction = LocalLayoutDirection.current

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingAction,
        containerColor = t.color.bgSurface,
        contentColor = t.color.textPrimary,
    ) { inset ->
        content(
            PaddingValues(
                start = inset.calculateStartPadding(direction) + pagePadding,
                top = inset.calculateTopPadding(),
                end = inset.calculateEndPadding(direction) + pagePadding,
                bottom = inset.calculateBottomPadding(),
            ),
        )
    }
}

/** Convenience for bodies that want no page inset at all. */
val NoPagePadding = 0.dp
