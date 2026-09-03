package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Time picker dialog wrapping the M3 clock, token-wired through the
 * CanvasTheme T2 bridge. [initialHour]/[initialMinute] set the starting time
 * (24h values); selection reported via [onTimeSelected].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasTimePicker(
    visible: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialHour: Int = 12,
    initialMinute: Int = 0,
    is24Hour: Boolean = false,
    confirmText: String = "OK",
    dismissText: String = "Cancel",
) {
    val t = LocalSemanticTokens.current
    if (visible) {
        val state = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute,
            is24Hour = is24Hour,
        )
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
                Column(
                    modifier = Modifier.padding(t.space.lg),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TimePicker(
                        state = state,
                        colors = TimePickerDefaults.colors(
                            selectorColor = t.color.accentPrimary,
                            periodSelectorSelectedContainerColor = t.color.accentPrimary,
                            periodSelectorSelectedContentColor = t.color.accentOnPrimary,
                            timeSelectorSelectedContainerColor = t.color.accentPrimary,
                            timeSelectorSelectedContentColor = t.color.accentOnPrimary,
                        ),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = t.space.md),
                    ) {
                        Spacer(Modifier.weight(1f))
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = dismissText,
                                style = TextFromType(t.type.label, weight = FontWeight.Medium),
                                color = t.color.textSecondary,
                            )
                        }
                        Spacer(Modifier.width(t.space.xs))
                        TextButton(onClick = {
                            onTimeSelected(state.hour, state.minute)
                            onDismiss()
                        }) {
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
