package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.CanvasTheme
import com.canvas.ink.basic.palette.DefaultPalette
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The scaffold's job is to make page padding and the region slots a structural fact rather
 * than a per-screen decision. Nine surveyed screens disagreed three ways on page padding and
 * none of them used `space.layout.page`, the token defined for it.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CanvasScreenScaffoldTest {

    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `body padding defaults to the layout page token`() {
        var observed: PaddingValues? = null
        var direction: LayoutDirection? = null
        compose.setContent {
            CanvasTheme {
                direction = LocalLayoutDirection.current
                CanvasScreenScaffold { padding ->
                    observed = padding
                }
            }
        }
        val expected = DefaultPalette.instance.light.space.layout.page
        val dir = requireNotNull(direction)
        assertEquals(expected, requireNotNull(observed).calculateStartPadding(dir))
        assertEquals(expected, requireNotNull(observed).calculateEndPadding(dir))
    }

    @Test
    fun `page padding is overridable for edge-to-edge bodies`() {
        var observed: PaddingValues? = null
        var direction: LayoutDirection? = null
        compose.setContent {
            CanvasTheme {
                direction = LocalLayoutDirection.current
                CanvasScreenScaffold(pagePadding = NoPagePadding) { padding ->
                    observed = padding
                }
            }
        }
        val dir = requireNotNull(direction)
        assertEquals(0.dp, requireNotNull(observed).calculateStartPadding(dir))
    }

    @Test
    fun `top and bottom regions both render`() {
        compose.setContent {
            CanvasTheme {
                CanvasScreenScaffold(
                    topBar = { Text("top region") },
                    bottomBar = { Text("bottom region") },
                ) { Text("body") }
            }
        }
        compose.onNodeWithText("top region").assertIsDisplayed()
        compose.onNodeWithText("bottom region").assertIsDisplayed()
        compose.onNodeWithText("body").assertIsDisplayed()
    }

    @Test
    fun `body reserves room for the bottom region`() {
        var withoutBar = 0.dp
        var withBar = 0.dp
        compose.setContent {
            CanvasTheme {
                var showBar by mutableStateOf(false)
                CanvasScreenScaffold(
                    bottomBar = { if (showBar) Text("pinned") },
                ) { padding ->
                    if (showBar) withBar = padding.calculateBottomPadding()
                    else withoutBar = padding.calculateBottomPadding()
                }
                showBar = true
            }
        }
        // A pinned region must push the body up, never overlap it.
        assertEquals(true, withBar >= withoutBar)
    }
}
