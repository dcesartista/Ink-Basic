package com.canvas.ui.default.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import com.canvas.ui.default.token.TypeStyle

/**
 * Converts a T3 [TypeStyle] into a Compose [TextStyle]. Central bridge so
 * components never hand-roll text properties.
 */
@Composable
fun TextFromType(
    style: TypeStyle,
    fontSize: TextUnit? = null,
    weight: Int? = null,
    lineHeight: TextUnit? = null,
): TextStyle = TextStyle(
    fontSize = fontSize ?: style.size,
    fontWeight = weight?.let { androidx.compose.ui.text.font.FontWeight(it) }
        ?: androidx.compose.ui.text.font.FontWeight(style.weight),
    lineHeight = lineHeight ?: style.lineHeight,
    letterSpacing = style.letterSpacing,
)
