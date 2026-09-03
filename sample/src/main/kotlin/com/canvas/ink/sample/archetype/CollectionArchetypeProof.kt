package com.canvas.ink.sample.archetype

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.canvas.ink.basic.component.CanvasSegmentedControl
import com.canvas.ink.basic.component.CanvasTopBar
import com.canvas.ink.basic.component.TextFromType
import com.canvas.ink.basic.layout.CanvasCollection
import com.canvas.ink.basic.layout.CanvasPageShell
import com.canvas.ink.basic.layout.CollectionDensity
import com.canvas.ink.basic.layout.CollectionItemSlots
import com.canvas.ink.basic.layout.CollectionRating
import com.canvas.ink.basic.palette.CanvasTheme
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Proof for Palette ADR-0003: **one archetype, one call site, two reference kits.**
 *
 * The Collection archetype is invoked exactly once below. Toggling `density` and which
 * optional slots are supplied reproduces two screens from unrelated commercial UI kits:
 *
 *  - **Editorial** — Open Fashion's Category grid. Title and price only; no rating, no
 *    compare-at price, no description. Sparse by intent.
 *  - **Marketplace** — Stylish's product listing. Every optional slot filled: description,
 *    compare-at price, discount label, rating with review count.
 *
 * Nothing about the call changes between them except the data and two enum values. That is
 * the property the ADR exists to guarantee, and the reason archetypes name slots rather than
 * components — had the contract said `ListItem` or `Card`, neither kit could be expressed
 * without the other's layout leaking in.
 */

private data class DemoProduct(
    val id: Int,
    val title: String,
    val brand: String,
    val price: String,
    val was: String? = null,
    val off: String? = null,
    val rating: CollectionRating? = null,
    val tint: Color,
)

private val EDITORIAL = listOf(
    DemoProduct(1, "Recycle Boucle Knit Cardigan", "MOHAN", "$120", tint = Color(0xFFE8E3DD)),
    DemoProduct(2, "Oversized Wool Coat", "LAMEREI", "$340", tint = Color(0xFFD8D2CB)),
    DemoProduct(3, "Angora Reversible Cardigan", "21WN", "$120", tint = Color(0xFFEFEAE4)),
    DemoProduct(4, "Signature Sweatshirt", "5252 BY O!OI", "$98", tint = Color(0xFFDCD6CE)),
)

private val MARKETPLACE = listOf(
    DemoProduct(
        1, "Nike Sneakers", "Vision Alta Men's Shoes Size (All Colours)",
        "₹1,500", "₹2,999", "50% Off", CollectionRating(4.2f, 56890), Color(0xFFE4E9EF),
    ),
    DemoProduct(
        2, "Black Winter Jacket", "Autumn And Winter Casual cotton-padded jacket",
        "₹499", "₹1,299", "61% Off", CollectionRating(4.5f, 6890), Color(0xFFD9DEE5),
    ),
    DemoProduct(
        3, "Mens Starry Shirt", "Starry Sky Printed Shirt 100% Cotton Fabric",
        "₹399", "₹999", "60% Off", CollectionRating(4.1f, 152344), Color(0xFFE9EDF2),
    ),
    DemoProduct(
        4, "Realme 7", "6 GB RAM | 64 GB ROM | Expandable Upto 256 GB",
        "₹3,499", "₹4,999", "30% Off", CollectionRating(3.9f, 344567), Color(0xFFDDE3EA),
    ),
)

private enum class Flavour { Editorial, Marketplace }

@Composable
fun CollectionArchetypeProof() {
    var flavour by remember { mutableStateOf(Flavour.Editorial) }
    var density by remember { mutableStateOf(CollectionDensity.Grid2) }
    val t = LocalSemanticTokens.current

    val items = if (flavour == Flavour.Editorial) EDITORIAL else MARKETPLACE

    CanvasPageShell(
        topBar = {
            CanvasTopBar(
                title = if (flavour == Flavour.Editorial) "APPAREL" else "52,082+ Items",
            )
        },
    ) { padding ->
        CanvasCollection(
            items = items,
            key = { it.id },
            density = density,
            contentPadding = padding,
            // Slot supply is the ONLY thing that differs between the two kits.
            slots = { p ->
                CollectionItemSlots(
                    title = p.title,
                    supporting = if (flavour == Flavour.Editorial) p.brand else p.brand,
                    price = p.price,
                    priceCompare = p.was,
                    discountLabel = p.off,
                    rating = p.rating,
                )
            },
            // The app owns WHICH media; the ink owns how large and what shape — it hands
            // the modifier in. A real app swaps this Box for its image loader unchanged.
            media = { p, inkModifier -> Box(inkModifier.background(p.tint)) },
            header = {
                Controls(
                    flavour = flavour,
                    density = density,
                    onFlavour = { flavour = it },
                    onDensity = { density = it },
                )
            },
            footer = {
                Text(
                    text = "— end of results —",
                    style = TextFromType(t.type.labelSmall),
                    color = t.color.textTertiary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = t.space.lg),
                )
            },
        )
    }
}

@Composable
private fun Controls(
    flavour: Flavour,
    density: CollectionDensity,
    onFlavour: (Flavour) -> Unit,
    onDensity: (CollectionDensity) -> Unit,
) {
    val t = LocalSemanticTokens.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = t.space.sm),
        verticalArrangement = Arrangement.spacedBy(t.space.xs),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(t.space.sm)) {
            CanvasSegmentedControl(
                options = listOf("Editorial", "Marketplace"),
                selectedIndex = flavour.ordinal,
                onSelect = { onFlavour(Flavour.entries[it]) },
                modifier = Modifier.weight(1f),
            )
        }
        CanvasSegmentedControl(
            options = listOf("Grid", "Rows"),
            selectedIndex = density.ordinal,
            onSelect = { onDensity(CollectionDensity.entries[it]) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(name = "Collection — editorial grid", heightDp = 720)
@Composable
private fun PreviewEditorial() {
    CanvasTheme { CollectionArchetypeProof() }
}
