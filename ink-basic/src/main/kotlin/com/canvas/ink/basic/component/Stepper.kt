package com.canvas.ink.basic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Numbered step progress indicator. Each step renders a circle (completed =
 * accent fill with check, current = accent ring, upcoming = alt surface); a
 * connector line joins consecutive steps.
 */
@Composable
fun CanvasStepper(
    steps: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier,
    checkIcon: ImageVector? = null,
) {
    val t = LocalSemanticTokens.current
    val circleSize = t.sizing.densityCompact
    Row(modifier = modifier.fillMaxWidth()) {
        steps.forEachIndexed { index, label ->
            val isCompleted = index < currentStep
            val isCurrent = index == currentStep
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (index > 0) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(t.border.thick)
                                .padding(end = t.space.xxs)
                                .clip(RoundedCornerShape(t.radius.pill))
                                .background(
                                    if (isCompleted) t.color.accentPrimary else t.color.bgSurfaceAlt
                                ),
                        )
                    }
                    Box(modifier = Modifier.size(circleSize)) {
                        CanvasStepperCircle(
                            isCompleted = isCompleted,
                            isCurrent = isCurrent,
                            number = index + 1,
                            checkIcon = checkIcon,
                        )
                    }
                    if (index < steps.lastIndex) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(t.border.thick)
                                .padding(start = t.space.xxs)
                                .clip(RoundedCornerShape(t.radius.pill))
                                .background(
                                    if (isCompleted) t.color.accentPrimary else t.color.bgSurfaceAlt
                                ),
                        )
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
                Text(
                    text = label,
                    style = TextFromType(t.type.labelSmall),
                    color = if (isCurrent) t.color.textPrimary else t.color.textSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = t.space.xxs),
                )
            }
        }
    }
}

/** Single numbered/checked circle in a [CanvasStepper]. */
@Composable
private fun CanvasStepperCircle(
    isCompleted: Boolean,
    isCurrent: Boolean,
    number: Int,
    checkIcon: ImageVector?,
) {
    val t = LocalSemanticTokens.current
    val size = t.sizing.densityCompact
    val ringWidth = t.border.thick
    when {
        isCompleted -> Box(
            modifier = Modifier
                .size(size)
                .background(t.color.accentPrimary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (checkIcon != null) {
                Icon(
                    imageVector = checkIcon,
                    contentDescription = null,
                    tint = t.color.accentOnPrimary,
                    modifier = Modifier.size(t.sizing.iconSizeSm),
                )
            } else {
                Text(
                    text = "✓",
                    style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                    color = t.color.accentOnPrimary,
                )
            }
        }
        isCurrent -> Box(
            modifier = Modifier
                .size(size)
                .background(t.color.bgSurface, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(size - ringWidth * 2)
                    .background(t.color.accentPrimary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = number.toString(),
                    style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                    color = t.color.accentOnPrimary,
                )
            }
        }
        else -> Box(
            modifier = Modifier
                .size(size)
                .background(t.color.bgSurfaceAlt, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number.toString(),
                style = TextFromType(t.type.label, weight = FontWeight.Medium),
                color = t.color.textTertiary,
            )
        }
    }
}
