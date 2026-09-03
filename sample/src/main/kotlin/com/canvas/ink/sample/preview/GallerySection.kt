package com.canvas.ink.sample.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.component.TextFromType
import com.canvas.ink.basic.palette.CanvasTheme
import com.canvas.ink.basic.palette.LocalSemanticTokens

/** Wraps gallery content in the library theme so tokens resolve in @Previews. */
@Composable
fun CanvasThemePreview(content: @Composable () -> Unit) {
    CanvasTheme(content = content)
}

/** A horizontal row of components aligned center; used to lay atoms side-by-side. */
@Composable
fun GalleryRow(
    modifier: Modifier = Modifier,
    content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit,
) {
    val t = LocalSemanticTokens.current
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(t.space.sm),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        content = content,
    )
}

/** A titled, raised section showing one or more components stacked vertically. */
@Composable
fun GallerySection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val t = LocalSemanticTokens.current
    Column(modifier = modifier) {
        Text(
            text = title,
            style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
            color = t.color.textPrimary,
        )
        Spacer(Modifier.height(t.space.xs))
        Surface(
            color = t.color.bgSurfaceRaised,
            tonalElevation = t.elevation.sm,
            shape = RoundedCornerShape(t.radius.lg),
        ) {
            Column(
                modifier = Modifier.padding(t.space.md),
                verticalArrangement = Arrangement.spacedBy(t.space.sm),
                content = content,
            )
        }
    }
}
