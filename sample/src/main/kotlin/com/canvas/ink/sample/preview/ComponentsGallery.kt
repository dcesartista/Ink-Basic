package com.canvas.ink.sample.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.canvas.ink.basic.component.CanvasBanner
import com.canvas.ink.basic.component.CanvasBannerTone
import com.canvas.ink.basic.component.CanvasCard
import com.canvas.ink.basic.component.CanvasEmptyState
import com.canvas.ink.basic.component.CanvasErrorState
import com.canvas.ink.basic.component.CanvasFilter
import com.canvas.ink.basic.component.CanvasListItem
import com.canvas.ink.basic.component.CanvasSearchBar
import com.canvas.ink.basic.component.CanvasSegmentedControl
import com.canvas.ink.basic.component.CanvasSelect
import com.canvas.ink.basic.component.CanvasSlider
import com.canvas.ink.basic.component.CanvasSnackbar
import com.canvas.ink.basic.component.CanvasStepper
import com.canvas.ink.basic.component.CanvasTextField
import com.canvas.ink.basic.component.CanvasTextFieldFilled
import com.canvas.ink.basic.component.CanvasToast
import com.canvas.ink.basic.component.CanvasTooltip

@Composable
fun ComponentsGallery() {
    GallerySection("Inputs") {
        var text by remember { mutableStateOf("") }
        CanvasTextField(value = text, onValueChange = { text = it }, label = "Username", modifier = Modifier.fillMaxWidth())
        CanvasTextFieldFilled(value = text, onValueChange = { text = it }, label = "Full name", modifier = Modifier.fillMaxWidth())
        CanvasSearchBar(value = text, onValueChange = { text = it }, placeholder = "Search", modifier = Modifier.fillMaxWidth())
        var slider by remember { mutableStateOf(0.4f) }
        CanvasSlider(value = slider, onValueChange = { slider = it }, label = "Load", valueLabel = "${(slider * 100).toInt()}%", modifier = Modifier.fillMaxWidth())
        val regions = listOf("North", "South", "East")
        var region by remember { mutableStateOf(regions[0]) }
        CanvasSelect(options = regions, label = { it }, selected = region, onSelect = { region = it }, modifier = Modifier.fillMaxWidth())
        var seg by remember { mutableStateOf(0) }
        CanvasSegmentedControl(options = listOf("Day", "Week"), selectedIndex = seg, onSelect = { seg = it }, modifier = Modifier.fillMaxWidth())
        var filters by remember { mutableStateOf(listOf("Active")) }
        CanvasFilter(options = listOf("Active", "Paused", "Draft"), label = { it }, selected = filters, onToggle = { f -> filters = if (f in filters) filters - f else filters + f })
        CanvasStepper(steps = listOf("Details", "Payment", "Review"), currentStep = 1, modifier = Modifier.fillMaxWidth())
    }

    GallerySection("Feedback") {
        CanvasBanner(message = "Your profile is 80% complete.", tone = CanvasBannerTone.Info, icon = Icons.Default.Info, actionLabel = "Complete")
        CanvasBanner(message = "Saved successfully.", tone = CanvasBannerTone.Success)
        CanvasBanner(message = "Something went wrong.", tone = CanvasBannerTone.Error)
        CanvasToast(message = "Item removed")
        CanvasSnackbar(message = "Changes saved", actionLabel = "Undo", onAction = {})
        CanvasTooltip(text = "A helpful hint")
    }

    GallerySection("Containers") {
        CanvasCard {
            CanvasListItem(title = "Design tokens", supportingText = "Reconciled against the original library")
            CanvasListItem(title = "Components", supportingText = "Port complete")
        }
        CanvasEmptyState(title = "No results", supportingText = "Try adjusting your search", icon = Icons.Default.Search)
        CanvasErrorState(title = "Could not load", message = "Check your connection", retryText = "Retry", onRetry = {})
    }
}

