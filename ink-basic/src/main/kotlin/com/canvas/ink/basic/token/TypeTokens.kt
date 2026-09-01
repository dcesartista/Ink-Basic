package com.canvas.ink.basic.token

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * T3 type token — a single typography style. fontFamily is resolved by the
 * consumer (a palette may swap it for a branded face).
 */
data class TypeStyle(
    val size: TextUnit,
    val weight: Int,
    val lineHeight: TextUnit,
    val letterSpacing: TextUnit,
)

/** T3 type scale. Components read styles; they never set raw text sizes. */
data class TypeTokens(
    val display: TypeStyle,
    val h1: TypeStyle,
    val h2: TypeStyle,
    val h3: TypeStyle,
    val h4: TypeStyle,
    val body: TypeStyle,
    val bodySmall: TypeStyle,
    val label: TypeStyle,
    val labelSmall: TypeStyle,
    val caption: TypeStyle,
) {
    companion object {
        /** Default (neutral, framework-friendly) type scale, 2026 modern-Android proportions. */
        fun defaultScale(): TypeTokens = TypeTokens(
            display = TypeStyle(size = 40.sp, weight = 600, lineHeight = 48.sp, letterSpacing = (-0.5).sp),
            h1 = TypeStyle(size = 32.sp, weight = 600, lineHeight = 40.sp, letterSpacing = (-0.5).sp),
            h2 = TypeStyle(size = 26.sp, weight = 600, lineHeight = 32.sp, letterSpacing = 0.sp),
            h3 = TypeStyle(size = 20.sp, weight = 600, lineHeight = 26.sp, letterSpacing = 0.2.sp),
            h4 = TypeStyle(size = 16.sp, weight = 600, lineHeight = 22.sp, letterSpacing = 0.2.sp),
            body = TypeStyle(size = 15.sp, weight = 400, lineHeight = 22.sp, letterSpacing = 0.3.sp),
            bodySmall = TypeStyle(size = 13.sp, weight = 400, lineHeight = 18.sp, letterSpacing = 0.3.sp),
            label = TypeStyle(size = 14.sp, weight = 500, lineHeight = 18.sp, letterSpacing = 0.4.sp),
            labelSmall = TypeStyle(size = 12.sp, weight = 500, lineHeight = 16.sp, letterSpacing = 0.5.sp),
            caption = TypeStyle(size = 11.sp, weight = 400, lineHeight = 14.sp, letterSpacing = 0.5.sp),
        )
    }
}

/**
 * The single T3 -> Compose text conversion. Both the T2 typography bridge and
 * the `TextFromType` component helper resolve through here, so a component and
 * a stray stock widget cannot render the same token differently.
 */
fun TypeStyle.toTextStyle(
    fontSize: TextUnit? = null,
    weight: FontWeight? = null,
    lineHeight: TextUnit? = null,
): TextStyle = TextStyle(
    fontSize = fontSize ?: size,
    fontWeight = weight ?: FontWeight(this.weight),
    lineHeight = lineHeight ?: this.lineHeight,
    letterSpacing = letterSpacing,
)
