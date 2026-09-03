package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.Dp
import com.canvas.ink.basic.palette.CanvasTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The Collection archetype's contract (Palette ADR-0003).
 *
 * The property under test is the one the whole slot model rests on: **the same call renders
 * different screens**. If density or slot supply ever stops being the only difference between
 * an editorial catalogue and a marketplace listing, inks collapse back into re-skins.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CanvasCollectionTest {
    @get:Rule
    val compose = createComposeRule()

    private data class Item(val id: Int, val name: String)

    private val items = listOf(Item(1, "First"), Item(2, "Second"))

    private fun slotsWithEverything(item: Item) = CollectionItemSlots(
        title = item.name,
        supporting = "supporting copy",
        price = "$10",
        priceCompare = "$20",
        discountLabel = "50% Off",
        rating = CollectionRating(4.5f, 99),
    )

    private fun slotsMinimal(item: Item) = CollectionItemSlots(title = item.name)

    @Test
    fun `grid density renders every item`() {
        compose.setContent {
            CanvasTheme {
                CanvasCollection(
                    items = items,
                    key = { it.id },
                    slots = ::slotsMinimal,
                    density = CollectionDensity.Grid2,
                )
            }
        }
        compose.onNodeWithText("First").assertIsDisplayed()
        compose.onNodeWithText("Second").assertIsDisplayed()
    }

    @Test
    fun `row density renders the same items from the same call`() {
        compose.setContent {
            CanvasTheme {
                CanvasCollection(
                    items = items,
                    key = { it.id },
                    slots = ::slotsMinimal,
                    density = CollectionDensity.RowCompact,
                )
            }
        }
        compose.onNodeWithText("First").assertIsDisplayed()
        compose.onNodeWithText("Second").assertIsDisplayed()
    }

    @Test
    fun `optional slots are omitted when the app has no data for them`() {
        compose.setContent {
            CanvasTheme {
                CanvasCollection(
                    items = items,
                    key = { it.id },
                    slots = ::slotsMinimal,
                )
            }
        }
        compose.onNodeWithText("First").assertIsDisplayed()
        compose.onNodeWithText("supporting copy").assertDoesNotExist()
        compose.onNodeWithText("$20").assertDoesNotExist()
        compose.onNodeWithText("50% Off").assertDoesNotExist()
    }

    @Test
    fun `optional slots render when supplied`() {
        compose.setContent {
            CanvasTheme {
                CanvasCollection(
                    items = items.take(1),
                    key = { it.id },
                    slots = ::slotsWithEverything,
                    density = CollectionDensity.RowCompact,
                )
            }
        }
        compose.onNodeWithText("supporting copy").assertIsDisplayed()
        compose.onNodeWithText("$20").assertIsDisplayed()
        compose.onNodeWithText("50% Off").assertIsDisplayed()
        compose.onNodeWithText("★ 4.5").assertIsDisplayed()
    }

    /**
     * The app owns *which* media; the ink owns *how large and what shape*. If the ink stopped
     * passing a modifier the app would be deciding layout, and the archetype would be
     * decorative rather than contractual.
     */
    @Test
    fun `the ink supplies the media modifier, and it differs by density`() {
        val received = mutableMapOf<CollectionDensity, Modifier>()
        compose.setContent {
            CanvasTheme {
                Column {
                    CollectionDensity.entries.forEach { density ->
                        Box(Modifier.weight(1f)) {
                            CanvasCollection(
                                items = items.take(1),
                                key = { it.id },
                                slots = ::slotsMinimal,
                                density = density,
                                media = { _, inkModifier ->
                                    received[density] = inkModifier
                                    Box(Modifier.size(Dp.Hairline))
                                },
                            )
                        }
                    }
                }
            }
        }
        compose.waitForIdle()
        assertEquals(CollectionDensity.entries.size, received.size)
        CollectionDensity.entries.forEach { assertNotNull(received[it]) }
        // Grid sizes media by aspect ratio, rows by a fixed token — never the same modifier.
        assert(received[CollectionDensity.Grid2] != received[CollectionDensity.RowCompact]) {
            "each density must impose its own media geometry"
        }
    }
}
