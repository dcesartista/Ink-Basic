package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Modal bottom sheet handler. Renders the sheet when [visible] is true; dismiss
 * on swipe/scrim. A remembered sheet state is provided for common use.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val t = LocalSemanticTokens.current
    if (visible) {
        val state = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = state,
            containerColor = t.color.bgSurfaceRaised,
            contentColor = t.color.textPrimary,
            shape = RoundedCornerShape(topStart = t.radius.lg, topEnd = t.radius.lg),
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = t.space.layout.page)
                    .navigationBarsPadding()
                    .padding(bottom = t.space.md),
                content = content,
            )
        }
    }
}
