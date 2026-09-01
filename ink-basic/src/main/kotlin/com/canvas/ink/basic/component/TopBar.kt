package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * App top bar with title, optional navigation/action slots. Surface bg + page
 * padding; flat elevation by default (64dp bar).
 */
@Composable
fun CanvasTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val t = LocalSemanticTokens.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(t.sizing.appBarHeight)
            .padding(horizontal = t.space.layout.page),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (navigationIcon != null) {
            navigationIcon()
            Spacer(Modifier.width(t.space.sm))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
            color = t.color.textPrimary,
            modifier = Modifier.weight(1f),
            maxLines = 1,
        )
        actions()
    }
}
