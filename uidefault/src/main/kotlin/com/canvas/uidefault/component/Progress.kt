package com.canvas.uidefault.component

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.canvas.uidefault.palette.LocalSemanticTokens

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
        LinearProgressIndicator(
            progress = { progress ?: Float.NaN },
            modifier = Modifier
                .fillMaxWidth()
                .height(t.sizing.progressThickness),
            color = t.color.accentPrimary,
            trackColor = t.color.bgSurfaceAlt,
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
            gapSize = 0.dp,
        )
    }
}
