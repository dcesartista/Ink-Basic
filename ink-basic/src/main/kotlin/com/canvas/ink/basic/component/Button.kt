package com.canvas.ink.basic.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Primary action button. Filled with the accent; text uses the matching on*
 * token so contrast holds in every theme (ADR-0001 on* contract).
 */
@Composable
fun CanvasButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val t = LocalSemanticTokens.current
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.heightIn(min = t.sizing.touchTarget),
        contentPadding = PaddingValues(horizontal = t.space.md, vertical = t.space.sm),
        colors = ButtonDefaults.buttonColors(
            containerColor = t.color.accentPrimary,
            contentColor = t.color.accentOnPrimary,
            disabledContainerColor = t.color.bgSurfaceAlt,
            disabledContentColor = t.color.textDisabled,
        ),
        shape = RoundedCornerShape(t.radius.md),
    ) {
        androidx.compose.material3.Text(
            text = text,
            style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/**
 * Secondary/outlined action. Surface bg + outline + primary text — a
 * lower-emphasis companion to [CanvasButton].
 */
@Composable
fun CanvasButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val t = LocalSemanticTokens.current
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.heightIn(min = t.sizing.touchTarget),
        contentPadding = PaddingValues(horizontal = t.space.md, vertical = t.space.sm),
        border = BorderStroke(1.dp, t.color.outline),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = t.color.bgSurface,
            contentColor = t.color.textPrimary,
            disabledContainerColor = t.color.bgSurfaceAlt,
            disabledContentColor = t.color.textDisabled,
        ),
        shape = RoundedCornerShape(t.radius.md),
    ) {
        androidx.compose.material3.Text(
            text = text,
            style = TextFromType(t.type.label, weight = FontWeight.Medium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
