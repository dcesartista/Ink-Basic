package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * A spotlight-style informational callout. Renders a raised card overlay (host
 * positions it over the target region) with title, message, and a next/dismiss
 * action. Step text helps orient multi-step flows.
 */
@Composable
fun CanvasCoachmark(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    actionText: String = "Got it",
    onAction: (() -> Unit)? = null,
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
                    Text(
                        text = title,
                        style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
                        color = t.color.textPrimary,
                    )
                    Spacer(Modifier.height(t.space.xs))
                    Text(
                        text = message,
                        style = TextFromType(t.type.body),
                        color = t.color.textSecondary,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = t.space.md),
                    ) {
                        Spacer(Modifier.weight(1f))
                        TextButton(onClick = onAction ?: onDismiss) {
                            Text(
                                text = actionText,
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
