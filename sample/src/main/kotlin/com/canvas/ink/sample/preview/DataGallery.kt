package com.canvas.ink.sample.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.component.CanvasBottomSheet
import com.canvas.ink.basic.component.CanvasDatePicker
import com.canvas.ink.basic.component.CanvasDialog
import com.canvas.ink.basic.component.CanvasListItem
import com.canvas.ink.basic.component.CanvasPullToRefresh
import com.canvas.ink.basic.component.CanvasStories
import com.canvas.ink.basic.component.CanvasStory
import com.canvas.ink.basic.component.CanvasTimePicker
import com.canvas.ink.basic.component.CanvasTimeline
import com.canvas.ink.basic.component.CanvasTimelineItem

@Composable
fun DataGallery() {
    var dialog by remember { mutableStateOf(false) }
    var sheet by remember { mutableStateOf(false) }
    var datePicker by remember { mutableStateOf(false) }
    var timePicker by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }
    var date by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var timeH by remember { mutableIntStateOf(12) }
    var timeM by remember { mutableIntStateOf(0) }

    GallerySection("Pick & prompt controls") {
        Button(onClick = { dialog = true }) { Text("Open dialog") }
        Button(onClick = { sheet = true }) { Text("Open bottom sheet") }
        Button(onClick = { datePicker = true }) { Text("Pick date") }
        Button(onClick = { timePicker = true }) { Text("Pick time") }
    }

    GallerySection("Timeline") {
        CanvasTimeline(
            items = listOf(
                CanvasTimelineItem(title = "Order placed", supportingText = "Invoice #1042", timestamp = "Today, 09:12", icon = Icons.Default.Check),
                CanvasTimelineItem(title = "Shipped", supportingText = "DHL tracking attached", timestamp = "Yesterday", icon = Icons.Default.Send),
                CanvasTimelineItem(title = "Delivered", timestamp = "Pending", icon = Icons.Default.MailOutline),
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }

    GallerySection("Stories") {
        CanvasStories(
            stories = listOf(
                CanvasStory(label = "You", initials = "DC", seen = true),
                CanvasStory(label = "Design", initials = "AR"),
                CanvasStory(label = "Eng", initials = "LM"),
                CanvasStory(label = "Product", initials = "JP"),
            ),
            onStoryClick = {},
        )
    }

    GallerySection("Pull to refresh") {
        CanvasPullToRefresh(
            isRefreshing = refreshing,
            onRefresh = {
                refreshing = true
                // Simulated refresh completion.
            },
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Swipe down to refresh")
            }
        }
    }

    CanvasDialog(
        visible = dialog,
        onDismiss = { dialog = false },
        title = "Delete this draft?",
        message = "This action cannot be undone.",
        confirmText = "Delete",
        onConfirm = { dialog = false },
        dismissText = "Cancel",
    )

    CanvasBottomSheet(visible = sheet, onDismiss = { sheet = false }) {
        CanvasListItem(title = "Share", leading = { CanvasM3Icon(Icons.Default.Send) })
        CanvasListItem(title = "Copy link", leading = { CanvasM3Icon(Icons.Default.Send) })
    }

    CanvasDatePicker(
        visible = datePicker,
        onDismiss = { datePicker = false },
        onDateSelected = { date = it },
        confirmText = "OK",
        dismissText = "Cancel",
    )

    CanvasTimePicker(
        visible = timePicker,
        onDismiss = { timePicker = false },
        onTimeSelected = { h, m -> timeH = h; timeM = m },
    )
}

@Composable
private fun CanvasM3Icon(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    androidx.compose.material3.Icon(imageVector = icon, contentDescription = null)
}

@Preview(name = "Data gallery", widthDp = 360, heightDp = 1400)
@Composable
fun DataGalleryPreview() {
    CanvasThemePreview {
        DataGallery()
    }
}
