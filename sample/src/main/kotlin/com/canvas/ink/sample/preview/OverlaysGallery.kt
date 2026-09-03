package com.canvas.ink.sample.preview

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.canvas.ink.basic.component.CanvasCoachmark
import com.canvas.ink.basic.component.CanvasGuideStep
import com.canvas.ink.basic.component.CanvasGuideTour

@Composable
fun OverlaysGallery() {
    var coachmark by remember { mutableStateOf(true) }
    var tourStep by remember { mutableIntStateOf(-1) }
    var tourVisible by remember { mutableStateOf(false) }

    val steps = listOf(
        CanvasGuideStep(title = "Welcome", message = "Here is what you can do with Ink Basic."),
        CanvasGuideStep(title = "Create", message = "Use the FAB to create a new record."),
        CanvasGuideStep(title = "Analyze", message = "Open reports to track progress."),
    )

    if (tourVisible) {
        CanvasGuideTour(
            steps = steps,
            currentStep = tourStep.coerceIn(0, steps.lastIndex),
            onStepChange = { tourStep = it },
            onDismiss = { tourVisible = false },
        )
    }

    CanvasCoachmark(
        visible = coachmark && !tourVisible,
        onDismiss = { coachmark = false },
        title = "New here?",
        message = "Tap the highlighted area to get started.",
        actionText = "Got it",
        onAction = { coachmark = false },
    )

    Button(onClick = { tourVisible = true; tourStep = 0; coachmark = false }) {
        Text("Start guide tour")
    }
}

@Preview(name = "Overlays gallery", widthDp = 360, heightDp = 600)
@Composable
fun OverlaysGalleryPreview() {
    CanvasThemePreview {
        OverlaysGallery()
    }
}
