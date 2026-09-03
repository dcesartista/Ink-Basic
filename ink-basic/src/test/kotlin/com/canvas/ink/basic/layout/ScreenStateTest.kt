package com.canvas.ink.basic.layout

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure-JVM validation of the phase model. No Android, no Compose.
 *
 * The point of the sealed hierarchy is that mutually-exclusive phases cannot co-occur —
 * the defect parallel `loading`/`error` fields allowed. These tests pin that property and
 * the helpers screens rely on.
 */
class ScreenStateTest {

    @Test
    fun `content exposes its value`() {
        val state: ScreenState<List<Int>> = ScreenState.Content(listOf(1, 2, 3))
        assertEquals(listOf(1, 2, 3), state.valueOrNull)
    }

    @Test
    fun `non-content phases expose no value`() {
        assertNull(ScreenState.Loading.valueOrNull)
        assertNull(ScreenState.Empty().valueOrNull)
        assertNull(ScreenState.Error("boom").valueOrNull)
    }

    @Test
    fun `isLoading is true only while loading`() {
        assertTrue(ScreenState.Loading.isLoading)
        assertFalse(ScreenState.Empty().isLoading)
        assertFalse(ScreenState.Error("boom").isLoading)
        assertFalse(ScreenState.Content(Unit).isLoading)
    }

    @Test
    fun `empty carries an optional reason so one layout serves several empties`() {
        assertNull(ScreenState.Empty().reason)
        assertEquals("Type to search", ScreenState.Empty("Type to search").reason)
    }

    @Test
    fun `error message is optional`() {
        assertNull(ScreenState.Error().message)
        assertEquals("offline", ScreenState.Error("offline").message)
    }

    /**
     * Guards the invariant the type exists for: adding a phase must be a deliberate,
     * compile-visible act. If this `when` stops being exhaustive the build breaks here
     * rather than silently rendering nothing on some screen.
     */
    @Test
    fun `phases are exhaustive`() {
        val all: List<ScreenState<Unit>> = listOf(
            ScreenState.Loading,
            ScreenState.Empty(),
            ScreenState.Error("e"),
            ScreenState.Content(Unit),
        )
        val labels = all.map { state ->
            when (state) {
                is ScreenState.Loading -> "loading"
                is ScreenState.Empty -> "empty"
                is ScreenState.Error -> "error"
                is ScreenState.Content -> "content"
            }
        }
        assertEquals(listOf("loading", "empty", "error", "content"), labels)
    }
}
