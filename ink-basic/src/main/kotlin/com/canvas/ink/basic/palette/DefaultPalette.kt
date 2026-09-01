package com.canvas.ink.basic.palette

import androidx.compose.ui.graphics.Color
import com.canvas.ink.basic.token.ColorTokens
import com.canvas.ink.basic.token.ElevationTokens
import com.canvas.ink.basic.token.MotionTokens
import com.canvas.ink.basic.token.RadiusTokens
import com.canvas.ink.basic.token.SemanticTokens
import com.canvas.ink.basic.token.SizingTokens
import com.canvas.ink.basic.token.SpaceTokens
import com.canvas.ink.basic.token.TypeTokens

/**
 * The free default "look" — hand-crafted as a coherent designed palette, not a
 * Material auto-pick (ADR-0001). Warm, calm, editorial: warm-neutral surfaces
 * with a single indigo accent. Replace this object entirely to rebrand; the
 * component contract is untouched.
 */
object DefaultPalette {

    const val ID = "default"

    /** The complete, strict palette: all three modes resolve. */
    val instance: Palette = Palette(
        id = ID,
        light = light(),
        dark = dark(),
        highContrast = highContrast(),
    )

    private fun light(): SemanticTokens = SemanticTokens(
        color = ColorTokens(
            bgSurface = Color(0xFFFBF9F7),
            bgSurfaceAlt = Color(0xFFF1EDE8),
            bgSurfaceRaised = Color(0xFFFFFFFF),
            textPrimary = Color(0xFF211D19),
            textSecondary = Color(0xFF5C554D),
            textTertiary = Color(0xFF8A8177),
            textDisabled = Color(0xFFB4ACA2),
            textInverse = Color(0xFFFFFFFF),
            accentPrimary = Color(0xFF4F46E5),
            accentOnPrimary = Color(0xFFFFFFFF),
            accentSecondary = Color(0xFF7C3AED),
            accentOnSecondary = Color(0xFFFFFFFF),
            error = Color(0xFFB3261E),
            onError = Color(0xFFFFFFFF),
            warning = Color(0xFF8A5A00),
            onWarning = Color(0xFFFFFFFF),
            success = Color(0xFF1B7A4B),
            onSuccess = Color(0xFFFFFFFF),
            info = Color(0xFF1976D2),
            onInfo = Color(0xFFFFFFFF),
            outline = Color(0xFF8A8177),
            divider = Color(0xFFE5DFD8),
            overlay = Color(0x4D211D19),
        ),
        type = defaultType(),
        space = SpaceTokens(),
        radius = RadiusTokens(),
        elevation = ElevationTokens(),
        motion = MotionTokens(),
        sizing = SizingTokens(),
    )

    private fun dark(): SemanticTokens = SemanticTokens(
        color = ColorTokens(
            bgSurface = Color(0xFF151311),
            bgSurfaceAlt = Color(0xFF211D19),
            bgSurfaceRaised = Color(0xFF2A2521),
            textPrimary = Color(0xFFEDE8E2),
            textSecondary = Color(0xFFB8B0A6),
            textTertiary = Color(0xFF8A8177),
            textDisabled = Color(0xFF605A52),
            textInverse = Color(0xFF211D19),
            accentPrimary = Color(0xFF8B85FF),
            accentOnPrimary = Color(0xFF1A1440),
            accentSecondary = Color(0xFFB79CFF),
            accentOnSecondary = Color(0xFF26144A),
            error = Color(0xFFF2B8B5),
            onError = Color(0xFF601410),
            warning = Color(0xFFFCCE64),
            onWarning = Color(0xFF4A3400),
            success = Color(0xFF81C995),
            onSuccess = Color(0xFF0B3B1E),
            info = Color(0xFF8AB4F8),
            onInfo = Color(0xFF0B2A5E),
            outline = Color(0xFF8A8177),
            divider = Color(0xFF3A352F),
            overlay = Color(0x8A000000),
        ),
        type = defaultType(),
        space = SpaceTokens(),
        radius = RadiusTokens(),
        elevation = ElevationTokens(),
        motion = MotionTokens(),
        sizing = SizingTokens(),
    )

    private fun highContrast(): SemanticTokens = SemanticTokens(
        color = ColorTokens(
            bgSurface = Color(0xFFFFFFFF),
            bgSurfaceAlt = Color(0xFFE8E4DE),
            bgSurfaceRaised = Color(0xFFFFFFFF),
            textPrimary = Color(0xFF000000),
            textSecondary = Color(0xFF1A1A1A),
            textTertiary = Color(0xFF3A3A3A),
            textDisabled = Color(0xFF555555),
            textInverse = Color(0xFFFFFFFF),
            accentPrimary = Color(0xFF0000FF),
            accentOnPrimary = Color(0xFFFFFFFF),
            accentSecondary = Color(0xFF6A00C4),
            accentOnSecondary = Color(0xFFFFFFFF),
            error = Color(0xFFC00),
            onError = Color(0xFFFFFFFF),
            warning = Color(0xFF7A4D00),
            onWarning = Color(0xFFFFFFFF),
            success = Color(0xFF006B2B),
            onSuccess = Color(0xFFFFFFFF),
            info = Color(0xFF0047AB),
            onInfo = Color(0xFFFFFFFF),
            outline = Color(0xFF4A4A4A),
            divider = Color(0xFF000000),
            overlay = Color(0x66000000),
        ),
        type = defaultType(),
        space = SpaceTokens(),
        radius = RadiusTokens(),
        elevation = ElevationTokens(),
        motion = MotionTokens(),
        sizing = SizingTokens(),
    )

    /** Shared type scale across modes (identical structure; palettes may override text sizes per mode later). */
    private fun defaultType(): TypeTokens = TypeTokens.defaultScale()
}
