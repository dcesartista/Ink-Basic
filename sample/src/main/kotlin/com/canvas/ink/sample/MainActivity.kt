package com.canvas.ink.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.CanvasTheme
import com.canvas.ink.sample.preview.AtomsGallery
import com.canvas.ink.sample.preview.ComponentsGallery
import com.canvas.ink.sample.preview.DataGallery
import com.canvas.ink.sample.preview.NavigationGallery
import com.canvas.ink.sample.preview.OverlaysGallery

/**
 * Sample app that hosts the component gallery so every Canvas component can be
 * inspected at runtime and via @Preview composables.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasTheme {
                SampleHome()
            }
        }
    }
}

@Composable
fun SampleHome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        AtomsGallery()
        ComponentsGallery()
        NavigationGallery()
        DataGallery()
        OverlaysGallery()
    }
}
