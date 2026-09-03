package com.canvas.ink.basic.layout

/**
 * The content an app supplies for one item of the Collection archetype.
 *
 * These are **values, not widgets** (Palette ADR-0003). The app says what the item *means*;
 * the ink decides what it looks like — which typography, which arrangement, how much of it
 * survives at a given density. That split is the whole reason two inks can render the same
 * screen as an elevated card, a compact row or a masonry tile without the app changing.
 *
 * Optional slots are genuinely optional: an ink that has nowhere to put [rating] omits it,
 * and an app with no rating data simply leaves it null. Neither is an error.
 */
data class CollectionItemSlots(
    /** required */
    val title: String,
    /** optional — a second line of description */
    val supporting: String? = null,
    /** recommended — already formatted for display; the ink never does currency maths */
    val price: String? = null,
    /** optional — the struck-through "was" price */
    val priceCompare: String? = null,
    /** optional — e.g. "50% Off" */
    val discountLabel: String? = null,
    /** optional */
    val rating: CollectionRating? = null,
)

/** Rating as a value; the ink chooses stars, a bar, a number, or to drop it entirely. */
data class CollectionRating(
    val value: Float,
    val count: Int? = null,
)

/**
 * How densely the collection renders. Each is a realization of the same archetype, not a
 * different archetype — the reference kits toggle between them at runtime from a toolbar.
 *
 * An ink declares which densities it supports; ink-basic ships two.
 */
enum class CollectionDensity {
    /** Two columns, media-led. The default browse density. */
    Grid2,

    /** One column, media leading, richer metadata. Scannable, more text per item. */
    RowCompact,
}
