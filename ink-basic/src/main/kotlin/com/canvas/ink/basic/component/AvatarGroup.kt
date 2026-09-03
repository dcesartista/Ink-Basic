package com.canvas.ink.basic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * Overlapping stack of avatars. Renders up to [max] avatars, each overlapping
 * the previous by ~1/3 of its width, then a "+N" overflow badge. Feed painters
 * via [images] or initials via [initials].
 */
@Composable
fun CanvasAvatarGroup(
    modifier: Modifier = Modifier,
    avatarSize: Dp = 32.dp,
    max: Int = 4,
    images: List<Painter> = emptyList(),
    initials: List<String> = emptyList(),
) {
    val t = LocalSemanticTokens.current
    val palette = listOf(
        t.color.accentPrimary,
        t.color.accentSecondary,
        t.color.info,
        t.color.success,
        t.color.warning,
    )
    val total = if (images.isNotEmpty()) images.size else initials.size
    val shown = minOf(total, max)
    val overflow = total - shown
    val ring = t.border.medium
    val overlap = avatarSize * 0.33f
    val ringColor = t.color.bgSurface

    Row(modifier = modifier) {
        for (i in 0 until shown) {
            val isImage = images.isNotEmpty()
            val x = if (i == 0) 0.dp else -overlap * i + ring
            Box(
                modifier = Modifier
                    .offset(x = x)
                    .background(ringColor, CircleShape)
                    .padding(ring)
                    .size(avatarSize - ring * 2),
                contentAlignment = Alignment.Center,
            ) {
                CanvasAvatar(
                    size = avatarSize - ring * 2,
                    image = images.getOrNull(i),
                    text = initials.getOrNull(i),
                    color = if (isImage) null else palette[i % palette.size],
                )
            }
        }
        if (overflow > 0) {
            Box(
                modifier = Modifier
                    .offset(x = -overlap * shown + ring)
                    .background(t.color.textSecondary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                CanvasAvatar(
                    size = avatarSize - ring * 2,
                    text = "+$overflow",
                    color = t.color.textInverse,
                )
            }
        }
    }
}
