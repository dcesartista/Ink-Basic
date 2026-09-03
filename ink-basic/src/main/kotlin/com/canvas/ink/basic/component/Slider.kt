package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Single-value slider. Optional [label] and [valueLabel] (e.g. "30%") rendered
 * above the track.
 */
@Composable
fun CanvasSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    enabled: Boolean = true,
    label: String? = null,
    valueLabel: String? = null,
) {
    val t = LocalSemanticTokens.current
    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null) {
            Text(
                text = label,
                style = TextFromType(t.type.label),
                color = t.color.textSecondary,
            )
            if (valueLabel != null) {
                Text(
                    text = valueLabel,
                    style = TextFromType(t.type.label),
                    color = t.color.textPrimary,
                )
            }
            Spacer(Modifier.height(t.space.xxs))
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            enabled = enabled,
            colors = SliderDefaults.colors(
                thumbColor = t.color.accentPrimary,
                activeTrackColor = t.color.accentPrimary,
                inactiveTrackColor = t.color.bgSurfaceAlt,
                disabledThumbColor = t.color.textDisabled,
                disabledActiveTrackColor = t.color.bgSurfaceAlt,
                disabledInactiveTrackColor = t.color.bgSurfaceAlt,
            ),
        )
    }
}
