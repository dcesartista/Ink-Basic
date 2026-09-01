package com.canvas.ink.basic.component

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
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Error state: centered error-colored icon + title + message + optional retry.
 * Contrast is guaranteed by pairing the error fill with onError via the token.
 */
@Composable
fun CanvasErrorState(
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    icon: ImageVector? = null,
    retryText: String? = null,
    onRetry: (() -> Unit)? = null,
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
                tint = t.color.error,
                modifier = Modifier.size(t.sizing.iconSize * 2f),
            )
            Spacer(Modifier.height(t.space.lg))
        }
        Text(
            text = title,
            style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
            color = t.color.error,
            textAlign = TextAlign.Center,
        )
        if (message != null) {
            Spacer(Modifier.height(t.space.xs))
            Text(
                text = message,
                style = TextFromType(t.type.body),
                color = t.color.textSecondary,
                textAlign = TextAlign.Center,
            )
        }
        if (retryText != null && onRetry != null) {
            Spacer(Modifier.height(t.space.lg))
            CanvasButton(text = retryText, onClick = onRetry)
        }
    }
}
