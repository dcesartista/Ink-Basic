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
 * The original page frame. **Superseded by [CanvasPageShell] — prefer the shells for new work.**
 *
 * ## Why two of these exist
 *
 * This came first, when Palette ADR-0003 recognised only regions and phases. It owns the
 * three decisions every screen was otherwise making independently and inconsistently: where
 * the top region goes, where a pinned bottom region goes, and how much horizontal page
 * padding the body gets — resolved to `space.layout.page`, the token ADR-0001 defines for
 * exactly that and which no hand-rolled screen was using.
 *
 * ADR-0003 was then rewritten against external design references and gained the **shell**
 * concept: a screen's frame is one of three contracts, not one, and the choice is orthogonal
 * to its archetype.
 *
 * | Shell | Chrome | Bottom |
 * |---|---|---|
 * | [CanvasPageShell] | branded top bar | footer **or** tab bar |
 * | [CanvasOverlayShell] | dismiss affordance | optional pinned action, never a footer |
 * | [CanvasFocusedShell] | none | optional pinned action |
 *
 * [CanvasPageShell] is a near-drop-in replacement for this function — same slots, same
 * padding contract, same underlying frame — and additionally declares a [NavigationModel],
 * which this function cannot. That declaration matters: drawer versus bottom tabs is a
 * different navigation graph, not a different rendering, so it must be stated rather than
 * assumed.
 *
 * ## Why it has not simply been deleted
 *
 * It is public API in a published artifact and has existing callers. Removing it is a
 * breaking change and belongs in a deliberate deprecation cycle, not a drive-by edit.
 *
 * ## Intended resolution
 *
 * 1. Migrate remaining callers to [CanvasPageShell].
 * 2. Mark this `@Deprecated(ReplaceWith("CanvasPageShell(...)"))` for one minor version.
 * 3. Remove it in the following minor.
 *
 * Until step 1 is done, **this is not a second way of doing things you may pick between** —
 * it is a migration in progress. New screens use the shells.
 *
 * The [content] lambda receives the padding it must apply. A scrolling body should pass it
 * to the scroll container's `contentPadding` rather than to an outer `Modifier.padding`,
 * so content scrolls under the bars instead of being clipped by them — which is only correct
 * because the bars paint an opaque surface.
 *
 * @param pagePadding horizontal inset for the body. Pass `0.dp` for edge-to-edge bodies
 *   (a full-bleed media header, a list whose rows draw their own inset).
 * @see CanvasPageShell
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
