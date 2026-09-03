package com.canvas.ink.basic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Small status/count/notification badge. Renders a filled accent capsule;
 * content set via the trailing lambda or the [label]/[count] convenience
 * overloads.
 */
@Composable
fun CanvasBadge(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val t = LocalSemanticTokens.current
    Box(
        modifier = modifier
            .background(t.color.accentPrimary, RoundedCornerShape(t.radius.pill))
            .padding(horizontal = t.space.xs, vertical = t.space.xxs),
        contentAlignment = Alignment.Center,
        content = content,
    )
}

/** A labeled badge (text on the accent fill uses the on* pair for contrast). */
@Composable
fun CanvasBadge(
    label: String,
    modifier: Modifier = Modifier,
) {
    val t = LocalSemanticTokens.current
    CanvasBadge(modifier = modifier) {
        Text(
            text = label,
            style = TextFromType(t.type.labelSmall, weight = FontWeight.SemiBold),
            color = t.color.accentOnPrimary,
        )
    }
}

/** A numeric count badge (e.g. "3" on a notification icon). */
@Composable
fun CanvasBadge(
    count: Int,
    modifier: Modifier = Modifier,
    max: Int = 99,
) {
    CanvasBadge(
        label = if (count > max) "$max+" else count.toString(),
        modifier = modifier.wrapContentSize(Alignment.TopEnd),
    )
}
