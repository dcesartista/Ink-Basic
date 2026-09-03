package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.component.CanvasEmptyState
import com.canvas.ink.basic.component.CanvasErrorState
import com.canvas.ink.basic.component.CanvasProgress

/**
 * Renders exactly one of a [ScreenState]'s phases.
 *
 * This exists because hand-rolled `when { loading -> …; error -> … }` blocks drift: across
 * nine screens of one app they produced four different loading treatments, four different
 * error treatments, and three screens that silently rendered nothing at all when empty.
 * Routing every screen through one host makes those four phases a structural guarantee
 * instead of something each screen has to remember.
 *
 * Defaults are deliberately complete — a caller passing only [state] and [content] still
 * gets correct loading, empty and error rendering.
 *
 * [CanvasEmptyState] and [CanvasErrorState] centre themselves, so only the progress
 * indicator is aligned here.
 *
 * [emptyAction] exists because an empty phase is frequently *actionable* — "your cart is
 * empty, browse products", "log in to see this". Without it a caller has to abandon the host
 * and hand-roll the branch again, which is the drift this type exists to stop.
 */
@Composable
fun <T> CanvasStateHost(
    state: ScreenState<T>,
    modifier: Modifier = Modifier,
    emptyTitle: String = "Nothing here yet",
    emptyAction: (@Composable () -> Unit)? = null,
    errorTitle: String = "Something went wrong",
    retryText: String? = null,
    onRetry: (() -> Unit)? = null,
    content: @Composable BoxScope.(T) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is ScreenState.Loading ->
                CanvasProgress(modifier = Modifier.align(Alignment.Center))

            is ScreenState.Empty ->
                CanvasEmptyState(
                    title = state.reason ?: emptyTitle,
                    action = emptyAction,
                )

            is ScreenState.Error ->
                CanvasErrorState(
                    title = errorTitle,
                    message = state.message,
                    retryText = if (onRetry != null) retryText ?: "Retry" else null,
                    onRetry = onRetry,
                )

            is ScreenState.Content -> content(state.value)
        }
    }
}
