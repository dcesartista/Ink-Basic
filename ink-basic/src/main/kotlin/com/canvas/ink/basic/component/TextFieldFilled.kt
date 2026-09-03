package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Filled text input on the alt surface. Companion to the outlined
 * [CanvasTextField].
 */
@Composable
fun CanvasTextFieldFilled(
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
        TextField(
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
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                focusedContainerColor = t.color.bgSurfaceAlt,
                unfocusedContainerColor = t.color.bgSurfaceAlt,
                disabledContainerColor = t.color.bgSurfaceAlt,
                focusedTextColor = t.color.textPrimary,
                unfocusedTextColor = t.color.textPrimary,
                disabledTextColor = t.color.textDisabled,
                cursorColor = t.color.accentPrimary,
                focusedIndicatorColor = t.color.accentPrimary,
                unfocusedIndicatorColor = t.color.outline,
                errorIndicatorColor = t.color.error,
                focusedLabelColor = t.color.accentPrimary,
                unfocusedLabelColor = t.color.textSecondary,
                errorLabelColor = t.color.error,
                focusedPlaceholderColor = t.color.textTertiary,
                unfocusedPlaceholderColor = t.color.textTertiary,
            ),
        )
        if (error != null) {
            Text(
                text = error,
                style = TextFromType(t.type.bodySmall),
                color = t.color.error,
                modifier = Modifier.padding(top = t.space.xxs),
            )
        }
    }
}
