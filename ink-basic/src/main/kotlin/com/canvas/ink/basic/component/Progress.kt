package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Linear progress for determinate/indeterminate work, with optional label.
 */
@Composable
fun CanvasProgress(
    modifier: Modifier = Modifier,
    progress: Float? = null,
    label: String? = null,
) {
    val t = LocalSemanticTokens.current
    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = label,
                    style = TextFromType(t.type.label, weight = FontWeight.Medium),
                    color = t.color.textSecondary,
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(Modifier.height(t.space.xxs))
        }
        val bar = Modifier
            .fillMaxWidth()
            .height(t.sizing.progressThickness)
        // Indeterminate and determinate are DIFFERENT overloads. Passing
        // Float.NaN to the determinate one throws "current must not be NaN" on
        // composition, so a default CanvasProgress() call crashed the app.
        if (progress == null) {
            LinearProgressIndicator(
                modifier = bar,
                color = t.color.accentPrimary,
                trackColor = t.color.bgSurfaceAlt,
                strokeCap = StrokeCap.Round,
                gapSize = 0.dp,
            )
        } else {
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = bar,
                color = t.color.accentPrimary,
                trackColor = t.color.bgSurfaceAlt,
                strokeCap = StrokeCap.Round,
                gapSize = 0.dp,
            )
        }
    }
}
