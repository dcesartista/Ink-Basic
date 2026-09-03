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

/** A single guide-tour step. */
data class CanvasGuideStep(
    val title: String,
    val message: String,
)

/**
 * A sequential walkthrough rendered one step at a time. [steps] holds the
 * ordered content; [currentStep] drives the counter and prev/next flow, with
 * [onStepChange] and [onDismiss] handled in the host.
 */
@Composable
fun CanvasGuideTour(
    steps: List<CanvasGuideStep>,
    currentStep: Int,
    onStepChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    skipText: String = "Skip",
    nextText: String = "Next",
    doneText: String = "Done",
    backText: String = "Back",
) {
    val t = LocalSemanticTokens.current
    if (steps.isEmpty() || currentStep !in steps.indices) return
    val step = steps[currentStep]
    val isLast = currentStep == steps.lastIndex

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
                    text = "${currentStep + 1}/${steps.size}",
                    style = TextFromType(t.type.labelSmall),
                    color = t.color.textTertiary,
                )
                Spacer(Modifier.height(t.space.xxs))
                Text(
                    text = step.title,
                    style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
                    color = t.color.textPrimary,
                )
                Spacer(Modifier.height(t.space.xs))
                Text(
                    text = step.message,
                    style = TextFromType(t.type.body),
                    color = t.color.textSecondary,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = t.space.md),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    if (currentStep > 0) {
                        TextButton(onClick = { onStepChange(currentStep - 1) }) {
                            Text(
                                text = backText,
                                style = TextFromType(t.type.label, weight = FontWeight.Medium),
                                color = t.color.textSecondary,
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = skipText,
                            style = TextFromType(t.type.label, weight = FontWeight.Medium),
                            color = t.color.textSecondary,
                        )
                    }
                    Spacer(Modifier.width(t.space.xs))
                    TextButton(onClick = { if (isLast) onDismiss() else onStepChange(currentStep + 1) }) {
                        Text(
                            text = if (isLast) doneText else nextText,
                            style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                            color = t.color.accentPrimary,
                        )
                    }
                }
            }
        }
    }
}
