package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Text input with label, placeholder, and token-wired error state so validation
 * feedback stays consistent across every palette.
 */
@Composable
fun CanvasTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    error: String? = null,
    enabled: Boolean = true,
) {
    val t = LocalSemanticTokens.current
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            label = { Text(label) },
            placeholder = placeholder?.let { ph -> { Text(ph) } },
            isError = error != null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = t.sizing.touchTarget),
            shape = RoundedCornerShape(t.radius.md),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = t.color.accentPrimary,
                unfocusedBorderColor = t.color.outline,
                errorBorderColor = t.color.error,
                focusedLabelColor = t.color.accentPrimary,
                unfocusedLabelColor = t.color.textSecondary,
                errorLabelColor = t.color.error,
                cursorColor = t.color.accentPrimary,
                focusedTextColor = t.color.textPrimary,
                unfocusedTextColor = t.color.textPrimary,
                errorTextColor = t.color.error,
                focusedContainerColor = t.color.bgSurface,
                unfocusedContainerColor = t.color.bgSurface,
                disabledContainerColor = t.color.bgSurfaceAlt,
                disabledTextColor = t.color.textDisabled,
                focusedPlaceholderColor = t.color.textTertiary,
                unfocusedPlaceholderColor = t.color.textTertiary,
            ),
        )
        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = t.color.error,
                modifier = Modifier.padding(top = t.space.xxs),
            )
        }
    }
}
