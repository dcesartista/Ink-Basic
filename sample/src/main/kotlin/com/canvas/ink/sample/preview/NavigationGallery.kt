package com.canvas.ink.sample.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.canvas.ink.basic.component.CanvasAppBarLarge
import com.canvas.ink.basic.component.CanvasBottomNav
import com.canvas.ink.basic.component.CanvasIconButton
import com.canvas.ink.basic.component.CanvasTabRow
import com.canvas.ink.basic.component.CanvasTopBar
import com.canvas.ink.basic.component.NavDest

@Composable
fun NavigationGallery() {
    GallerySection("Top bars") {
        CanvasTopBar(
            title = "Inbox",
            navigationIcon = { CanvasIconButton(icon = Icons.Default.Menu, onClick = {}) },
            actions = {
                CanvasIconButton(icon = Icons.Default.Search, onClick = {})
                CanvasIconButton(icon = Icons.Default.Notifications, onClick = {})
            },
        )
        CanvasAppBarLarge(
            title = "Reports",
            supportingText = "Q3 performance summary across all teams",
            actions = {
                CanvasIconButton(icon = Icons.Default.Settings, onClick = {})
            },
        )
    }

    GallerySection("Tabs") {
        var tab by remember { mutableIntStateOf(0) }
        CanvasTabRow(tabs = listOf("Overview", "Analytics", "Settings"), selectedIndex = tab, onSelect = { tab = it }, modifier = Modifier.fillMaxWidth())
    }

    GallerySection("Bottom navigation") {
        var nav by remember { mutableIntStateOf(0) }
        CanvasBottomNav(
            destinations = listOf(
                NavDest("Home", Icons.Default.Home),
                NavDest("Search", Icons.Default.Search),
                NavDest("Profile", Icons.Default.Person),
            ),
            selectedIndex = nav,
            onSelect = { nav = it },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(name = "Navigation gallery", widthDp = 360, heightDp = 800)
@Composable
fun NavigationGalleryPreview() {
    CanvasThemePreview {
        NavigationGallery()
    }
}
