package com.kcmitch.gallery_dl.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UiStyleAndThemeSelectorBar(
    activeUiStyle: String, // "minimalist", "creator", "express", "terminal"
    activeThemePreset: String, // "periwinkle", "purple", "dark_slate"
    onSelectUiStyle: (String) -> Unit,
    onSelectThemePreset: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showThemePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top Layout Mode Selector
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Option 0: Minimalist Insget
                LayoutOptionChip(
                    title = "Minimalist",
                    icon = "⚡",
                    isSelected = activeUiStyle == "minimalist",
                    onClick = { onSelectUiStyle("minimalist") },
                    testTag = "ui_style_minimalist",
                    modifier = Modifier.weight(1f)
                )

                // Option 1: Creator Studio
                LayoutOptionChip(
                    title = "Studio",
                    icon = "📸",
                    isSelected = activeUiStyle == "creator",
                    onClick = { onSelectUiStyle("creator") },
                    testTag = "ui_style_creator",
                    modifier = Modifier.weight(1f)
                )

                // Option 2: Express
                LayoutOptionChip(
                    title = "Express",
                    icon = "🚀",
                    isSelected = activeUiStyle == "express",
                    onClick = { onSelectUiStyle("express") },
                    testTag = "ui_style_express",
                    modifier = Modifier.weight(1f)
                )

                // Option 3: Power CLI
                LayoutOptionChip(
                    title = "Terminal",
                    icon = "💻",
                    isSelected = activeUiStyle == "terminal",
                    onClick = { onSelectUiStyle("terminal") },
                    testTag = "ui_style_terminal",
                    modifier = Modifier.weight(1f)
                )

                // Theme Quick Toggle Button
                IconButton(
                    onClick = { showThemePicker = !showThemePicker },
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("theme_picker_toggle_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = "Change Theme",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Theme Palette Switcher Dropdown / Pills
        if (showThemePicker) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Choose Easy-on-the-Eyes Color Scheme:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ThemePresetPill(
                            name = "Slate Blue",
                            emoji = "🌊",
                            gradient = Brush.horizontalGradient(listOf(Color(0xFF5B67CA), Color(0xFF3B82F6))),
                            isSelected = activeThemePreset == "periwinkle" || activeThemePreset == "slate_blue",
                            onClick = { onSelectThemePreset("periwinkle") },
                            testTag = "theme_preset_periwinkle",
                            modifier = Modifier.weight(1f)
                        )

                        ThemePresetPill(
                            name = "Soft Purple",
                            emoji = "🪻",
                            gradient = Brush.horizontalGradient(listOf(Color(0xFF7C3AED), Color(0xFF9333EA))),
                            isSelected = activeThemePreset == "purple" || activeThemePreset == "soft_purple",
                            onClick = { onSelectThemePreset("purple") },
                            testTag = "theme_preset_purple",
                            modifier = Modifier.weight(1f)
                        )

                        ThemePresetPill(
                            name = "Dark Slate",
                            emoji = "🌌",
                            gradient = Brush.horizontalGradient(listOf(Color(0xFF1E293B), Color(0xFF0F172A))),
                            isSelected = activeThemePreset == "dark_slate" || activeThemePreset == "midnight",
                            onClick = { onSelectThemePreset("dark_slate") },
                            testTag = "theme_preset_dark_slate",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LayoutOptionChip(
    title: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
            .padding(horizontal = 2.dp)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = icon, fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ThemePresetPill(
    name: String,
    emoji: String,
    gradient: Brush,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(gradient)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 6.dp)
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = emoji, fontSize = 11.sp)
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                fontSize = 10.sp
            )
        }
    }
}
