package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Search input. . Pill-shaped, alt-surface
 * container; the search icon should be passed via [leadingIcon].
 */
@Composable
fun CanvasSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val t = LocalSemanticTokens.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        placeholder = placeholder?.let { ph -> { Text(ph) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = t.sizing.touchTarget),
        shape = RoundedCornerShape(t.radius.pill),
        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
            focusedBorderColor = t.color.accentPrimary,
            unfocusedBorderColor = t.color.bgSurfaceAlt,
            focusedContainerColor = t.color.bgSurfaceAlt,
            unfocusedContainerColor = t.color.bgSurfaceAlt,
            disabledContainerColor = t.color.bgSurfaceAlt,
            focusedTextColor = t.color.textPrimary,
            unfocusedTextColor = t.color.textPrimary,
            disabledTextColor = t.color.textDisabled,
            focusedPlaceholderColor = t.color.textTertiary,
            unfocusedPlaceholderColor = t.color.textTertiary,
            focusedLeadingIconColor = t.color.textSecondary,
            unfocusedLeadingIconColor = t.color.textSecondary,
            disabledLeadingIconColor = t.color.textDisabled,
            focusedTrailingIconColor = t.color.textSecondary,
            unfocusedTrailingIconColor = t.color.textSecondary,
        ),
    )
}
