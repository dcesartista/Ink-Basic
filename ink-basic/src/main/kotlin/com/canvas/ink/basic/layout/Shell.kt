package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * How an app moves between top-level destinations.
 *
 * Palette ADR-0003 pins this as **not an ink decision**: a drawer and a bottom tab bar are
 * different navigation graphs, not different renderings of one. An ink declares which model
 * its Page shell assumes; switching ink across models is surfaced to the user rather than
 * applied silently, because it changes app code.
 */
enum class NavigationModel {
    /** Top-level destinations live behind a menu affordance. */
    Drawer,

    /** Top-level destinations are always visible in a persistent bar. */
    Tabs,

    /** The shell hosts no top-level navigation (focused and overlay shells). */
    None,
}

/** The navigation model ink-basic's Page shell assumes. */
val InkBasicPageNavigationModel: NavigationModel = NavigationModel.Tabs

/**
 * **Page shell** — the everyday screen frame.
 *
 * Chrome above, a navigation or footer region below, and page inset applied to the body.
 * The [bottom] slot is where the two observed resolutions live: a page footer (Open Fashion)
 * or a tab bar (Stylish). Both are the same slot; the ink picks one and says so via
 * [navigationModel].
 *
 * The body receives the padding it must apply. A scrolling body should hand it to the scroll
 * container's `contentPadding` rather than an outer `Modifier.padding`, so content scrolls
 * under the chrome instead of being clipped by it — which is only correct because the bars
 * paint an opaque surface.
 */
@Composable
fun CanvasPageShell(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottom: @Composable () -> Unit = {},
    floatingAction: @Composable () -> Unit = {},
    pagePadding: Dp = LocalSemanticTokens.current.space.layout.page,
    @Suppress("UNUSED_PARAMETER") navigationModel: NavigationModel = InkBasicPageNavigationModel,
    content: @Composable (PaddingValues) -> Unit,
) {
    ShellFrame(
        modifier = modifier,
        topBar = topBar,
        bottom = bottom,
        floatingAction = floatingAction,
        pagePadding = pagePadding,
        content = content,
    )
}

/**
 * **Overlay shell** — a screen presented over another.
 *
 * Carries a dismiss affordance instead of navigation, and never a footer. Cart, menu, search
 * entry, immersive media and confirmation dialogs all sit here. A pinned action is still
 * allowed, and in the empty phase it commonly changes meaning rather than disappearing.
 */
@Composable
fun CanvasOverlayShell(
    modifier: Modifier = Modifier,
    dismiss: @Composable () -> Unit = {},
    pinnedAction: @Composable () -> Unit = {},
    pagePadding: Dp = LocalSemanticTokens.current.space.layout.page,
    content: @Composable (PaddingValues) -> Unit,
) {
    ShellFrame(
        modifier = modifier,
        topBar = dismiss,
        bottom = pinnedAction,
        floatingAction = {},
        pagePadding = pagePadding,
        content = content,
    )
}

/**
 * **Focused shell** — one task, no chrome at all.
 *
 * Auth, onboarding and full-screen prompts. There is deliberately no top bar slot: a focused
 * screen that needs a title puts it in its own body as a headline, which is what makes it
 * read as a moment rather than a page.
 */
@Composable
fun CanvasFocusedShell(
    modifier: Modifier = Modifier,
    pinnedAction: @Composable () -> Unit = {},
    pagePadding: Dp = LocalSemanticTokens.current.space.layout.page,
    content: @Composable (PaddingValues) -> Unit,
) {
    ShellFrame(
        modifier = modifier,
        topBar = {},
        bottom = pinnedAction,
        floatingAction = {},
        pagePadding = pagePadding,
        content = content,
    )
}

/** The one frame all three shells are cut from; differences above are contract, not layout. */
@Composable
private fun ShellFrame(
    modifier: Modifier,
    topBar: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
    floatingAction: @Composable () -> Unit,
    pagePadding: Dp,
    content: @Composable (PaddingValues) -> Unit,
) {
    val t = LocalSemanticTokens.current
    val direction = LocalLayoutDirection.current

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottom,
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
