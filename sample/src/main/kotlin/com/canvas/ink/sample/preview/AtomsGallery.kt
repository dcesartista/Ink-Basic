package com.canvas.ink.sample.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.canvas.ink.basic.component.CanvasAvatar
import com.canvas.ink.basic.component.CanvasAvatarGroup
import com.canvas.ink.basic.component.CanvasBadge
import com.canvas.ink.basic.component.CanvasButton
import com.canvas.ink.basic.component.CanvasButtonSecondary
import com.canvas.ink.basic.component.CanvasCheckbox
import com.canvas.ink.basic.component.CanvasChip
import com.canvas.ink.basic.component.CanvasChipStyle
import com.canvas.ink.basic.component.CanvasDivider
import com.canvas.ink.basic.component.CanvasFAB
import com.canvas.ink.basic.component.CanvasIconButton
import com.canvas.ink.basic.component.CanvasProgress
import com.canvas.ink.basic.component.CanvasRadioButton
import com.canvas.ink.basic.component.CanvasTag
import com.canvas.ink.basic.component.CanvasToggle

@Composable
fun AtomsGallery() {
    GallerySection("Atoms") {
        CanvasButton(text = "Primary", onClick = {}, modifier = Modifier.fillMaxWidth())
        CanvasButton(text = "Disabled", onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth())
        CanvasButtonSecondary(text = "Secondary", onClick = {}, modifier = Modifier.fillMaxWidth())
        CanvasDivider()
        GalleryRow {
            CanvasAvatar(text = "DC", contentDescription = "Dito Cesartista")
            CanvasBadge(count = 3)
            CanvasBadge(label = "New")
        }
        CanvasAvatarGroup(initials = listOf("AR", "LM", "JP", "DC"), max = 3)
        GalleryRow {
            CanvasChip(label = "Filter", style = CanvasChipStyle.Duotone, onClick = {})
            CanvasChip(label = "Selected", selected = true, onClick = {})
        }
        CanvasTag(label = "In Review")
        var checked by remember { mutableStateOf(false) }
        CanvasCheckbox(text = "Remember me", checked = checked, onCheckedChange = { checked = it }, modifier = Modifier.fillMaxWidth())
        var radio by remember { mutableStateOf(true) }
        CanvasRadioButton(text = "Weekly", selected = radio, onSelect = { radio = !radio }, modifier = Modifier.fillMaxWidth())
        var toggle by remember { mutableStateOf(false) }
        CanvasToggle(checked = toggle, onCheckedChange = { toggle = it }, label = "Notifications", modifier = Modifier.fillMaxWidth())
        CanvasProgress(progress = 0.6f, label = "Uploading 60%", modifier = Modifier.fillMaxWidth())
        GalleryRow {
            CanvasIconButton(icon = Icons.Default.Favorite, onClick = {}, contentDescription = "Like")
            CanvasIconButton(icon = Icons.Default.Star, onClick = {}, contentDescription = "Star")
            CanvasFAB(onClick = {}, icon = Icons.Default.Send)
        }
    }
}

@Preview(name = "Atoms gallery", widthDp = 360)
@Composable
fun AtomsGalleryPreview() {
    CanvasThemePreview {
        AtomsGallery()
    }
}
