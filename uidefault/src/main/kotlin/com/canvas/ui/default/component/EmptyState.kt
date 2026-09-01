package com.canvas.ui.default.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.canvas.ui.default.palette.LocalSemanticTokens

/**
 * Empty-state view: centered icon + title + optional supporting text/action.
 */
@Composable
fun CanvasEmptyState(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    supportingText: String? = null,
    action: (@Composable () -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(t.space.layout.section),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = t.color.textTertiary,
                modifier = Modifier.size(t.sizing.iconSize * 2f),
            )
            Spacer(Modifier.height(t.space.lg))
        }
        Text(
            text = title,
            style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
            color = t.color.textPrimary,
            textAlign = TextAlign.Center,
        )
        if (supportingText != null) {
            Spacer(Modifier.height(t.space.xs))
            Text(
                text = supportingText,
                style = TextFromType(t.type.body),
                color = t.color.textSecondary,
                textAlign = TextAlign.Center,
            )
        }
        if (action != null) {
            Spacer(Modifier.height(t.space.lg))
            action()
        }
    }
}
