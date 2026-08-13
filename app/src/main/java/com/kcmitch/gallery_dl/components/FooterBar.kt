package com.kcmitch.gallery_dl.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Bottom Footer Navigation Bar with 5 Equally Spaced Items:
 * 0: Terminal
 * 1: Placeholder (Tools)
 * 2: Homepage (Center - Thumb-sized circular add button)
 * 3: Gallery
 * 4: Settings
 */
@Composable
fun FooterBar(
    currentPage: Int,
    onPageSelected: (Int) -> Unit,
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Bottom Navigation Surface Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.95f),
            tonalElevation = 12.dp,
            shadowElevation = 16.dp,
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .height(68.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Button 0: Terminal
                FooterNavItem(
                    icon = Icons.Default.Terminal,
                    label = "Terminal",
                    isSelected = currentPage == 0,
                    onClick = { onPageSelected(0) },
                    modifier = Modifier.weight(1f)
                )

                // Button 1: Placeholder
                FooterNavItem(
                    icon = Icons.Default.Extension,
                    label = "Tools",
                    isSelected = currentPage == 1,
                    onClick = { onPageSelected(1) },
                    modifier = Modifier.weight(1f)
                )

                // Empty Center Slot for the raised Floating Add Button
                Spacer(modifier = Modifier.weight(1f))

                // Button 3: Gallery
                FooterNavItem(
                    icon = Icons.Default.Collections,
                    label = "Gallery",
                    isSelected = currentPage == 3,
                    onClick = { onPageSelected(3) },
                    modifier = Modifier.weight(1f)
                )

                // Button 4: Settings
                FooterNavItem(
                    icon = Icons.Default.Settings,
                    label = "Settings",
                    isSelected = currentPage == 4,
                    onClick = { onPageSelected(4) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Raised Floating Center Add Button (rises above the top of the footer bar)
        val isCenterActive = currentPage == 2
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    onPageSelected(2)
                    onAddButtonClick()
                },
                modifier = Modifier
                    .size(60.dp)
                    .shadow(elevation = 12.dp, shape = CircleShape)
                    .background(
                        color = if (isCenterActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Element / Home",
                    tint = if (isCenterActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun FooterNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
        animationSpec = tween(durationMillis = 200),
        label = "icon_color"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
            color = iconColor
        )
    }
}
