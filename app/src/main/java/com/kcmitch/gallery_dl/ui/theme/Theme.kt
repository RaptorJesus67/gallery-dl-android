package com.kcmitch.gallery_dl.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kcmitch.gallery_dl.data.PaletteManager

data class LinearGradientThemeColors(
    val solidBg: Color,
    val darkTopColor: Color,
    val lightBottomColor: Color
)

fun getLinearGradientThemeColors(themePreset: String): LinearGradientThemeColors {
    val palette = PaletteManager.getPaletteById(themePreset)
    return LinearGradientThemeColors(
        solidBg = palette.colorScheme.background,
        darkTopColor = palette.gradientTop,
        lightBottomColor = palette.gradientBottom
    )
}

@Composable
fun GalleryDlTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themePreset: String = "cobalt",
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val palette = PaletteManager.getPaletteById(themePreset)

    MaterialTheme(
        colorScheme = palette.colorScheme,
        typography = Typography,
        content = content
    )
}
