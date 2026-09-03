package com.canvas.ink.basic.layout

/**
 * The mutually-exclusive phases a screen body can be in.
 *
 * Modelled as a sealed hierarchy so "loading and error at once" is unrepresentable —
 * the defect that parallel `loading: Boolean` / `error: String?` fields invite, and which
 * every screen surveyed before this type existed had reinvented differently.
 *
 * A screen's state holder maps its own UI state onto this; the UI never decides a phase.
 */
sealed interface ScreenState<out T> {

    /** Work is in flight and there is nothing meaningful to show yet. */
    data object Loading : ScreenState<Nothing>

    /**
     * The screen resolved successfully to nothing to show.
     *
     * [reason] lets a caller distinguish *kinds* of empty that share one layout —
     * "no results for X" versus "type to search" — without a separate phase for each.
     */
    data class Empty(val reason: String? = null) : ScreenState<Nothing>

    /** The screen failed to resolve. [message] is user-facing copy, not a stack trace. */
    data class Error(val message: String? = null) : ScreenState<Nothing>

    /** The screen resolved to something to render. */
    data class Content<out T>(val value: T) : ScreenState<T>
}

/** True while [ScreenState.Loading] — for callers that also drive a pull-to-refresh. */
val ScreenState<*>.isLoading: Boolean get() = this is ScreenState.Loading

/** The content value, or null in every non-content phase. */
val <T> ScreenState<T>.valueOrNull: T?
    get() = (this as? ScreenState.Content<T>)?.value
