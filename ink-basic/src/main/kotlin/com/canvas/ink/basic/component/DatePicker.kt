package com.canvas.ink.basic.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Date picker dialog driven by the M3 calendar, token-wired through the
 * CanvasTheme T2 bridge. . Hoistable via
 * [initialTimestampMillis] / selected timestamp through [onDateSelected].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasDatePicker(
    visible: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    initialTimestampMillis: Long? = null,
    confirmText: String = "OK",
    dismissText: String = "Cancel",
) {
    val t = LocalSemanticTokens.current
    if (visible) {
        val state = rememberDatePickerState(initialSelectedDateMillis = initialTimestampMillis)
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let(onDateSelected)
                    onDismiss()
                }) {
                    Text(
                        text = confirmText,
                        style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                        color = t.color.accentPrimary,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = dismissText,
                        style = TextFromType(t.type.label, weight = FontWeight.Medium),
                        color = t.color.textSecondary,
                    )
                }
            },
            modifier = modifier,
        ) {
            DatePicker(state = state)
        }
    }
}
