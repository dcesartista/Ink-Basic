package com.canvas.ink.basic.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.lazy.items as columnItems
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.canvas.ink.basic.component.TextFromType
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * The **Collection** archetype: a set of peers, refinable, with a means of getting more.
 *
 * The app hands over values via [slots] and an optional [media] painter; ink-basic decides
 * the rest. Switching [density] re-renders the same data as a two-column grid or a compact
 * row without the call site changing — which is the property that lets one screen serve an
 * editorial catalogue and a marketplace listing.
 *
 * [media] receives a **Modifier from the ink**, not from the app. The app owns *which* image
 * (it holds the loader and the URL); the ink owns *how large and what shape*. Without that
 * split the app would be deciding layout, and the archetype would be decorative.
 *
 * [key] is required. An unkeyed lazy collection loses scroll position and re-composes on
 * every reorder — a defect that stays invisible until the data changes.
 */
@Composable
fun <T> CanvasCollection(
    items: List<T>,
    key: (T) -> Any,
    slots: (T) -> CollectionItemSlots,
    modifier: Modifier = Modifier,
    density: CollectionDensity = CollectionDensity.Grid2,
    contentPadding: PaddingValues = PaddingValues(),
    media: @Composable ((T, Modifier) -> Unit)? = null,
    itemAction: @Composable ((T) -> Unit)? = null,
    onItemClick: ((T) -> Unit)? = null,
    header: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current

    when (density) {
        CollectionDensity.Grid2 ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.fillMaxSize(),
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(t.space.layout.item),
                verticalArrangement = Arrangement.spacedBy(t.space.layout.section),
            ) {
                if (header != null) {
                    item(span = { GridItemSpan(2) }) {
                        header()
                    }
                }
                gridItems(items = items, key = key) { item ->
                    GridCell(item, slots(item), media, itemAction, onItemClick)
                }
                if (footer != null) {
                    item(span = { GridItemSpan(2) }) {
                        footer()
                    }
                }
            }

        CollectionDensity.RowCompact ->
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(t.space.layout.item),
            ) {
                if (header != null) item(key = "canvas-collection-header") { header() }
                columnItems(items = items, key = key) { item ->
                    RowCell(item, slots(item), media, itemAction, onItemClick)
                }
                if (footer != null) item(key = "canvas-collection-footer") { footer() }
            }
    }
}

@Composable
private fun <T> GridCell(
    item: T,
    slots: CollectionItemSlots,
    media: @Composable ((T, Modifier) -> Unit)?,
    itemAction: @Composable ((T) -> Unit)?,
    onItemClick: ((T) -> Unit)?,
) {
    val t = LocalSemanticTokens.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onItemClick != null) Modifier.clickable { onItemClick(item) } else Modifier),
        verticalArrangement = Arrangement.spacedBy(t.space.xs),
    ) {
        if (media != null) {
            media(
                item,
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(GRID_MEDIA_RATIO)
                    .clip(RoundedCornerShape(t.radius.sm)),
            )
        }
        Text(
            text = slots.title,
            style = TextFromType(t.type.body),
            color = t.color.textPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        slots.supporting?.let {
            Text(
                text = it,
                style = TextFromType(t.type.bodySmall),
                color = t.color.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        PriceLine(slots)
        slots.rating?.let { RatingLine(it) }
        itemAction?.invoke(item)
    }
}

@Composable
private fun <T> RowCell(
    item: T,
    slots: CollectionItemSlots,
    media: @Composable ((T, Modifier) -> Unit)?,
    itemAction: @Composable ((T) -> Unit)?,
    onItemClick: ((T) -> Unit)?,
) {
    val t = LocalSemanticTokens.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onItemClick != null) Modifier.clickable { onItemClick(item) } else Modifier),
        horizontalArrangement = Arrangement.spacedBy(t.space.layout.item),
    ) {
        if (media != null) {
            media(
                item,
                Modifier
                    .size(t.sizing.rowMedia)
                    .clip(RoundedCornerShape(t.radius.sm)),
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(t.space.xxs),
        ) {
            Text(
                text = slots.title,
                style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
                color = t.color.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            // The row density has room for supporting copy the grid has to drop.
            slots.supporting?.let {
                Text(
                    text = it,
                    style = TextFromType(t.type.bodySmall),
                    color = t.color.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            PriceLine(slots)
            slots.rating?.let { RatingLine(it) }
        }
        itemAction?.invoke(item)
    }
}

/** Price, its struck-through comparison and a discount label — one line, all optional. */
@Composable
private fun PriceLine(slots: CollectionItemSlots) {
    val t = LocalSemanticTokens.current
    if (slots.price == null && slots.priceCompare == null) return

    Row(
        horizontalArrangement = Arrangement.spacedBy(t.space.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        slots.price?.let {
            Text(
                text = it,
                style = TextFromType(t.type.body, weight = FontWeight.SemiBold),
                color = t.color.textPrimary,
            )
        }
        slots.priceCompare?.let {
            Text(
                text = it,
                style = TextFromType(t.type.bodySmall),
                color = t.color.textTertiary,
                textDecoration = TextDecoration.LineThrough,
            )
        }
        slots.discountLabel?.let {
            Text(
                text = it,
                style = TextFromType(t.type.labelSmall, weight = FontWeight.SemiBold),
                color = t.color.accentPrimary,
            )
        }
    }
}

/** Rating rendered as a value, not a widget the app supplied. */
@Composable
private fun RatingLine(rating: CollectionRating) {
    val t = LocalSemanticTokens.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(t.space.xxs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "★ ${rating.value}",
            style = TextFromType(t.type.labelSmall),
            color = t.color.warning,
        )
        rating.count?.let {
            Text(
                text = "($it)",
                style = TextFromType(t.type.labelSmall),
                color = t.color.textSecondary,
            )
        }
    }
}

private const val GRID_MEDIA_RATIO = 0.78f
