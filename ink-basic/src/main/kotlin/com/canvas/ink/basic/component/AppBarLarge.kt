package com.canvas.ink.basic.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Large app bar with a headline title and optional supporting line (a headline
 * on a surface, distinct from the standard [CanvasTopBar]).
 */
@Composable
fun CanvasAppBarLarge(
    title: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val t = LocalSemanticTokens.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(t.color.bgSurface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(t.sizing.appBarHeight)
                .padding(horizontal = t.space.layout.page),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (navigationIcon != null) {
                navigationIcon()
                Spacer(Modifier.width(t.space.sm))
            }
            Spacer(Modifier.weight(1f))
            actions()
        }
        Column(modifier = Modifier.padding(horizontal = t.space.layout.page, vertical = t.space.sm)) {
            Text(
                text = title,
                style = TextFromType(t.type.h2, weight = FontWeight.SemiBold),
                color = t.color.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (supportingText != null) {
                Spacer(Modifier.height(t.space.xxs))
                Text(
                    text = supportingText,
                    style = TextFromType(t.type.bodySmall),
                    color = t.color.textSecondary,
                )
            }
        }
    }
}
