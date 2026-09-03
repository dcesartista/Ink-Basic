package com.canvas.ink.basic.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/** Deterministic background color derived from a string (stable per input). */
private fun avatarColorFromSeed(seed: String, palette: List<Color>): Color {
    val hash = seed.fold(0) { acc, c -> (acc * 31 + c.code) and 0x7fffffff }
    return palette[hash % palette.size]
}

/**
 * Display image/icon/text in a circle. The image overload takes a [Painter]
 * (from a resource/Coil `rememberAsyncImagePainter`); the icon/initials
 * overloads cover the common cases.
 */
@Composable
fun CanvasAvatar(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    contentDescription: String? = null,
    image: Painter? = null,
    icon: ImageVector? = null,
    text: String? = null,
    color: Color? = null,
) {
    val t = LocalSemanticTokens.current
    val bg = color ?: avatarColorFromSeed(
        text ?: contentDescription ?: "x",
        listOf(
            t.color.accentPrimary,
            t.color.accentSecondary,
            t.color.info,
            t.color.success,
            t.color.warning,
        ),
    )
    Box(
        modifier = modifier
            .size(size)
            .background(bg, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        when {
            image != null -> Image(
                painter = image,
                contentDescription = contentDescription,
                modifier = Modifier.size(size),
                contentScale = ContentScale.Crop,
            )
            icon != null -> Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = t.color.textInverse,
                modifier = Modifier.size(size * 0.5f),
            )
            text != null -> {
                val initials = text
                    .trim()
                    .split(Regex("\\s+"))
                    .take(2)
                    .mapNotNull { it.firstOrNull() }
                    .joinToString("")
                    .uppercase()
                Text(
                    text = initials,
                    style = TextFromType(t.type.label, weight = FontWeight.SemiBold),
                    color = t.color.textInverse,
                )
            }
        }
    }
}
