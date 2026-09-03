package com.canvas.ink.basic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canvas.ink.basic.palette.LocalSemanticTokens

/** A story entry.  items. */
data class CanvasStory(
    val label: String,
    val image: Painter? = null,
    val initials: String? = null,
    val seen: Boolean = false,
)

/**
 * Horizontal row of story avatars with a seen/unseen progress ring, tap-to-open
 * semantics. .
 */
@Composable
fun CanvasStories(
    stories: List<CanvasStory>,
    onStoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    avatarSize: Dp = 56.dp,
) {
    val t = LocalSemanticTokens.current
    Row(modifier = modifier) {
        stories.forEachIndexed { index, story ->
            val ringSize = avatarSize + t.space.sm
            Column(
                modifier = Modifier
                    .padding(end = t.space.md)
                    .clickable { onStoryClick(index) },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(ringSize)
                        .clip(CircleShape)
                        .background(
                            if (story.seen) t.color.bgSurfaceAlt
                            else t.color.accentPrimary
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(avatarSize)
                            .padding(t.border.thick)
                            .clip(CircleShape)
                            .background(t.color.bgSurface),
                    ) {
                        CanvasAvatar(
                            size = avatarSize - t.border.thick * 2,
                            image = story.image,
                            text = story.initials,
                        )
                    }
                }
                Spacer(Modifier.height(t.space.xs))
                Text(
                    text = story.label,
                    style = TextFromType(t.type.labelSmall),
                    color = t.color.textSecondary,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(avatarSize + t.space.sm),
                )
            }
        }
    }
}
