package com.canvas.ui.default.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.canvas.ui.default.palette.LocalSemanticTokens

/**
 * Elevated content container. Uses surfaceRaised + elevation + radius; content
 * aligned to the token spacing scale.
 */
@Composable
fun CanvasCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(LocalSemanticTokens.current.radius.lg),
    content: @Composable ColumnScope.() -> Unit,
) {
    val t = LocalSemanticTokens.current
    val surfaceModifier = modifier
    val card = @Composable {
        Column(modifier = Modifier.padding(t.space.layout.item), content = content)
    }
    if (onClick != null) {
        Surface(
            onClick = onClick,
            modifier = surfaceModifier,
            shape = shape,
            color = t.color.bgSurfaceRaised,
            tonalElevation = t.elevation.sm,
            shadowElevation = t.elevation.sm,
        ) { card() }
    } else {
        Surface(
            modifier = surfaceModifier,
            shape = shape,
            color = t.color.bgSurfaceRaised,
            tonalElevation = t.elevation.sm,
            shadowElevation = t.elevation.sm,
        ) { card() }
    }
}
