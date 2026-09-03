package com.canvas.ink.basic.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.canvas.ink.basic.component.TextFromType
import com.canvas.ink.basic.palette.LocalSemanticTokens

/**
 * A titled block within a screen body, at the `space.layout.section` rhythm.
 *
 * The title carries a `heading` semantic, so assistive tech can navigate a long body by
 * section — the accessibility floor that hand-rolled section headers kept omitting.
 */
@Composable
fun CanvasSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    action: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val t = LocalSemanticTokens.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(t.space.layout.item),
    ) {
        if (title != null || action != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        style = TextFromType(t.type.h4, weight = FontWeight.SemiBold),
                        color = t.color.textPrimary,
                        modifier = Modifier.semantics { heading() },
                    )
                }
                action?.invoke()
            }
        }
        content()
    }
}
