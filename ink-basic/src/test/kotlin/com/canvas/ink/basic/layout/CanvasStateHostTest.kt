package com.canvas.ink.basic.layout

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.canvas.ink.basic.palette.CanvasTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Behaviour of the state host, on the JVM via Robolectric.
 *
 * These pin the guarantee the whole archetype layer rests on: **exactly one phase renders**.
 * Before the host existed, nine screens produced four loading treatments, four error
 * treatments, and three screens that rendered nothing when empty.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CanvasStateHostTest {

    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `content phase renders content and nothing else`() {
        compose.setContent {
            CanvasTheme {
                CanvasStateHost(state = ScreenState.Content("payload")) { value ->
                    Text(value)
                }
            }
        }
        compose.onNodeWithText("payload").assertIsDisplayed()
        compose.onNodeWithText("Nothing here yet").assertDoesNotExist()
        compose.onNodeWithText("Something went wrong").assertDoesNotExist()
    }

    @Test
    fun `empty phase renders the empty state and never the content lambda`() {
        var contentCalled = false
        compose.setContent {
            CanvasTheme {
                CanvasStateHost(state = ScreenState.Empty()) { _: String ->
                    contentCalled = true
                    Text("should not appear")
                }
            }
        }
        compose.onNodeWithText("Nothing here yet").assertIsDisplayed()
        compose.onNodeWithText("should not appear").assertDoesNotExist()
        assertTrue("content lambda must not run in the empty phase", !contentCalled)
    }

    @Test
    fun `empty reason overrides the default copy`() {
        compose.setContent {
            CanvasTheme {
                CanvasStateHost(state = ScreenState.Empty("Type to search")) { _: String -> }
            }
        }
        compose.onNodeWithText("Type to search").assertIsDisplayed()
        compose.onNodeWithText("Nothing here yet").assertDoesNotExist()
    }

    @Test
    fun `empty phase can carry an action`() {
        var acted = 0
        compose.setContent {
            CanvasTheme {
                CanvasStateHost(
                    state = ScreenState.Empty("Your cart is empty"),
                    emptyAction = {
                        androidx.compose.material3.TextButton(onClick = { acted++ }) {
                            Text("Browse")
                        }
                    },
                ) { _: String -> }
            }
        }
        compose.onNodeWithText("Your cart is empty").assertIsDisplayed()
        compose.onNodeWithText("Browse").performClick()
        assertTrue("empty action should have fired once, was $acted", acted == 1)
    }

    @Test
    fun `error phase renders title and message`() {
        compose.setContent {
            CanvasTheme {
                CanvasStateHost(
                    state = ScreenState.Error("no connection"),
                    errorTitle = "Could not load",
                ) { _: String -> }
            }
        }
        compose.onNodeWithText("Could not load").assertIsDisplayed()
        compose.onNodeWithText("no connection").assertIsDisplayed()
    }

    @Test
    fun `retry is offered only when a handler is supplied`() {
        compose.setContent {
            CanvasTheme {
                CanvasStateHost(state = ScreenState.Error("boom")) { _: String -> }
            }
        }
        compose.onNodeWithText("Retry").assertDoesNotExist()
    }

    @Test
    fun `retry invokes the handler`() {
        var retries = 0
        compose.setContent {
            CanvasTheme {
                CanvasStateHost(
                    state = ScreenState.Error("boom"),
                    onRetry = { retries++ },
                ) { _: String -> }
            }
        }
        compose.onNodeWithText("Retry").performClick()
        assertTrue("retry handler should have fired once, was $retries", retries == 1)
    }
}
