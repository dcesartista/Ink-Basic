package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Modal dialog / alert. .
 * Renders a centered raised card with title, message, and confirm/dismiss
 * actions. Content can be fully overridden via [content] (shown after
 * title/message).
 */
@Composable
fun CanvasDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    confirmText: String? = null,
    onConfirm: (() -> Unit)? = null,
    dismissText: String? = null,
    onDismissConfirm: (() -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current
    if (visible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(t.space.md),
                shape = RoundedCornerShape(t.radius.lg),
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = t.color.bgSurfaceRaised,
                    contentColor = t.color.textPrimary,
                ),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = t.elevation.lg),
            ) {
                Column(modifier = Modifier.padding(t.space.lg)) {
                    if (title != null) {
                        Text(
                            text = title,
                            style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
                            color = t.color.textPrimary,
                        )
                    }
                    if (message != null) {
                        Text(
                            text = message,
                            style = TextFromType(t.type.body),
                            color = t.color.textSecondary,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(top = t.space.xs),
                        )
                    }
                    if (content != null) {
                        Spacer(Modifier.height(t.space.sm))
                        content()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = t.space.lg),
                    ) {
                        if (dismissText != null) {
                            TextButton(
                                onClick = onDismissConfirm ?: onDismiss,
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = dismissText,
                                    style = TextFromType(t.type.label, weight = FontWeight.Medium),
                                    color = t.color.textSecondary,
                                )
                            }
                            Spacer(Modifier.width(t.space.sm))
                        }
                        if (confirmText != null) {
                            TextButton(
                                onClick = onConfirm ?: onDismiss,
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = confirmText,
                                    style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                                    color = t.color.accentPrimary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
