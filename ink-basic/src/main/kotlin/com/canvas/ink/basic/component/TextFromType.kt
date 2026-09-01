package com.canvas.ink.basic.component

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.canvas.ink.basic.token.TypeStyle

/**
 * Converts a T3 [TypeStyle] into a Compose [TextStyle]. Central bridge so
 * components never hand-roll text properties.
 */
fun TextFromType(
    style: TypeStyle,
    fontSize: TextUnit? = null,
    weight: FontWeight? = null,
    lineHeight: TextUnit? = null,
): TextStyle = TextStyle(
    fontSize = fontSize ?: style.size,
    fontWeight = weight ?: FontWeight(style.weight),
    lineHeight = lineHeight ?: style.lineHeight,
    letterSpacing = style.letterSpacing,
)
