package com.canvas.ink.basic.component

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.canvas.ink.basic.token.TypeStyle
import com.canvas.ink.basic.token.toTextStyle

/**
 * Converts a T3 [TypeStyle] into a Compose [TextStyle]. Central bridge so
 * components never hand-roll text properties, and never read
 * `MaterialTheme.typography` — that would resolve through the T2 bridge
 * instead of the token, which is the seam CANVAS's self-check rejects.
 */
fun TextFromType(
    style: TypeStyle,
    fontSize: TextUnit? = null,
    weight: FontWeight? = null,
    lineHeight: TextUnit? = null,
): TextStyle = style.toTextStyle(fontSize = fontSize, weight = weight, lineHeight = lineHeight)
