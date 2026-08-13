package com.kcmitch.gallery_dl.pages

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.AppConfig
import com.kcmitch.gallery_dl.components.BlankHeaderContainer
import com.kcmitch.gallery_dl.data.PaletteInfo
import com.kcmitch.gallery_dl.data.PaletteManager
import com.kcmitch.gallery_dl.pages.settings.License
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel
import com.kcmitch.gallery_dl.ui.components.CookieEditorDialog

/**
 * Settings Page (pages/Settings.kt)
 * Features:
 * - App Header Title via BlankHeaderContainer
 * - Color Palette Accordion with 3 Groups (Current, Site-Specific, Custom)
 * - 5-item visible limit with thin custom scrollbar
 * - Dynamic contrast and vibrant closed container styling
 * - Open Source License, Defaults, FAQs
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    viewModel: GalleryDlViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsState()
    val selectedSiteId by viewModel.selectedSiteId.collectAsState()
    val siteOptionsMap by viewModel.siteOptionsMap.collectAsState()

    var showCookieDialog by remember { mutableStateOf(false) }
    var expandedFaqIndex by remember { mutableStateOf<Int?>(null) }
    var showLicenseModal by remember { mutableStateOf(false) }
    var isPaletteAccordionOpen by remember { mutableStateOf(false) }

    val currentPalette = PaletteManager.getPaletteById(settings.themePreset)

    // Frozen order list while accordion is open so items don't shift during selection
    var frozenPalettes by remember { mutableStateOf<List<PaletteInfo>>(emptyList()) }
    val paletteScrollState = rememberLazyListState()

    LaunchedEffect(isPaletteAccordionOpen) {
        if (isPaletteAccordionOpen) {
            frozenPalettes = PaletteManager.buildGroupedList(settings.themePreset)
            paletteScrollState.scrollToItem(0)
        } else {
            paletteScrollState.scrollToItem(0)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // App Header Title Container
        item {
            BlankHeaderContainer()
        }

        // Page Title Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "App Settings & Reference",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${AppConfig.APP_NAME} v${AppConfig.VERSION_NAME}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section 1: Color Palettes Accordion Container
        item {
            val containerBgColor by animateColorAsState(
                targetValue = if (isPaletteAccordionOpen) {
                    if (currentPalette.isDarkBg) Color.White.copy(alpha = 0.08f)
                    else Color.Black.copy(alpha = 0.05f)
                } else {
                    currentPalette.closedContainerBg
                },
                label = "containerBg"
            )

            val arrowRotation by animateFloatAsState(
                targetValue = if (isPaletteAccordionOpen) 180f else 0f,
                label = "arrowRotation"
            )

            val textColor = if (isPaletteAccordionOpen) {
                MaterialTheme.colorScheme.onSurface
            } else {
                currentPalette.closedContainerTextColor
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = containerBgColor
                ),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isPaletteAccordionOpen) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                            else currentPalette.closedContainerTextColor.copy(alpha = 0.4f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Accordion Header Row (Clickable)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { isPaletteAccordionOpen = !isPaletteAccordionOpen },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = if (isPaletteAccordionOpen) MaterialTheme.colorScheme.primary else textColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Color Palettes",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = textColor
                                )
                                if (!isPaletteAccordionOpen) {
                                    Text(
                                        text = "Active: ${currentPalette.name} (Tap to change)",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = textColor.copy(alpha = 0.85f),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Toggle Palettes",
                            tint = if (isPaletteAccordionOpen) MaterialTheme.colorScheme.primary else textColor,
                            modifier = Modifier
                                .size(28.dp)
                                .rotate(arrowRotation)
                        )
                    }

                    // Expanded Accordion Content
                    AnimatedVisibility(visible = isPaletteAccordionOpen) {
                        Column {
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Select a color scheme below. Displays active scheme at top, followed by site themes and custom palettes.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            val scrollState = paletteScrollState
                            val displayList = if (frozenPalettes.isNotEmpty()) frozenPalettes else PaletteManager.buildGroupedList(settings.themePreset)
                            val scrollbarColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)

                            // 5-item visible window height (~280dp) with thin scrollbar indicator
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 280.dp)
                            ) {
                                LazyColumn(
                                    state = scrollState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 6.dp)
                                        .drawWithContent {
                                            drawContent()
                                            val visibleItems = scrollState.layoutInfo.visibleItemsInfo
                                            if (visibleItems.isNotEmpty() && scrollState.layoutInfo.totalItemsCount > 0) {
                                                val totalCount = scrollState.layoutInfo.totalItemsCount
                                                val firstIndex = visibleItems.first().index
                                                val viewportH = size.height
                                                val barHeight = (viewportH * (visibleItems.size.toFloat() / totalCount)).coerceAtLeast(30f)
                                                val barOffsetY = (firstIndex.toFloat() / totalCount) * viewportH

                                                drawRoundRect(
                                                    color = scrollbarColor,
                                                    topLeft = Offset(size.width - 10f, barOffsetY),
                                                    size = Size(8f, barHeight),
                                                    cornerRadius = CornerRadius(4f, 4f)
                                                )
                                            }
                                        },
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(displayList, key = { it.id }) { palette ->
                                        val isSelected = settings.themePreset == palette.id

                                        // Unclicked button styling: faded_container (whitish haze for dark bg, blackish for light)
                                        val itemBgColor = if (isSelected) {
                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f)
                                        } else {
                                            if (currentPalette.isDarkBg) {
                                                Color.White.copy(alpha = 0.08f)
                                            } else {
                                                Color.Black.copy(alpha = 0.05f)
                                            }
                                        }

                                        val itemBorderColor = if (isSelected) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            if (currentPalette.isDarkBg) Color.White.copy(alpha = 0.15f)
                                            else Color.Black.copy(alpha = 0.12f)
                                        }

                                        Surface(
                                            onClick = { viewModel.setThemePreset(palette.id) },
                                            shape = RoundedCornerShape(10.dp),
                                            color = itemBgColor,
                                            border = BorderStroke(
                                                width = if (isSelected) 2.dp else 1.dp,
                                                color = itemBorderColor
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(
                                                            text = palette.name,
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                                        )
                                                        if (isSelected) {
                                                            Spacer(modifier = Modifier.width(6.dp))
                                                            Icon(
                                                                imageVector = Icons.Default.CheckCircle,
                                                                contentDescription = "Active Palette",
                                                                tint = MaterialTheme.colorScheme.primary,
                                                                modifier = Modifier.size(16.dp)
                                                            )
                                                        }
                                                    }
                                                    Text(
                                                        text = palette.desc,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                                        fontSize = 11.sp
                                                    )
                                                }

                                                // Color Swatches preview
                                                Row(
                                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    palette.swatches.forEach { color ->
                                                        Box(
                                                            modifier = Modifier
                                                                .size(18.dp)
                                                                .clip(CircleShape)
                                                                .background(color)
                                                                .border(
                                                                    1.dp,
                                                                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                                                                    CircleShape
                                                                )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section 2: PROMINENT & UNBURIED LICENSE INFO SECTION (Faded Container)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Gavel,
                                contentDescription = "License",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Open Source License",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        AssistChip(
                            onClick = { showLicenseModal = true },
                            label = { Text("View Full Terms") },
                            leadingIcon = {
                                Icon(Icons.Default.Article, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = License.GALLERY_DL_COPYRIGHT,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Licensed under GNU General Public License v3.0 (GPL-3.0). Stored offline in app.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section 3: App Defaults & Configuration (Faded Container)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Download Defaults",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = settings.downloadDirectory,
                        onValueChange = { viewModel.setDownloadDirectory(it) },
                        label = { Text("Download Directory") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Folder, contentDescription = null) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = settings.rateLimit,
                            onValueChange = { viewModel.setRateLimit(it) },
                            label = { Text("Rate Limit (MB/s)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = settings.retries.toString(),
                            onValueChange = { it.toIntOrNull()?.let { r -> viewModel.setRetries(r) } },
                            label = { Text("Retries") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { showCookieDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Cookie, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Manage Cookies & Auth Keys")
                    }
                }
            }
        }

        // Section 4: Tutorial & Quick Start Guide (Faded Container)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tutorial & Quick Start",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val steps = listOf(
                        "1. Navigate to the Terminal page or Homepage to construct gallery-dl commands.",
                        "2. Select your desired site extractor (Instagram, Twitter, TikTok, Pixiv, etc.).",
                        "3. Input target URL or use cookies for login-restricted content.",
                        "4. Tap 'Execute' or type in the Terminal console to begin downloads."
                    )

                    steps.forEach { step ->
                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }

        // Section 5: FAQ Accordion Section (Faded Container)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.HelpOutline, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Frequently Asked Questions (FAQ)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val faqs = listOf(
                        "How do I download private posts or stories?" to "Export session cookies from your browser into Netscape cookie format and paste them using the 'Manage Cookies' dialog.",
                        "How do I use custom command arguments?" to "Swipe to the leftmost Terminal page and execute commands directly using Termux CLI syntax.",
                        "Where are downloaded files saved?" to "Files are saved in your chosen download directory (defaults to /sdcard/Download/gallery-dl)."
                    )

                    faqs.forEachIndexed { index, (q, a) ->
                        val isExpanded = expandedFaqIndex == index
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { expandedFaqIndex = if (isExpanded) null else index }
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = q,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null
                                )
                            }
                            if (isExpanded) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = a,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        if (index < faqs.size - 1) {
                            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                        }
                    }
                }
            }
        }
    }

    // Full Offline License Viewer Dialog
    if (showLicenseModal) {
        AlertDialog(
            onDismissRequest = { showLicenseModal = false },
            title = {
                Text(
                    text = License.LICENSE_NAME,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Box(
                    modifier = Modifier
                        .height(320.dp)
                        .background(Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    LazyColumn {
                        item {
                            Text(
                                text = License.FULL_LICENSE_TEXT,
                                color = Color(0xFFCBD5E1),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLicenseModal = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Cookie Editor Dialog
    if (showCookieDialog) {
        val currentOpt = siteOptionsMap[selectedSiteId]
        CookieEditorDialog(
            siteId = selectedSiteId,
            initialCookieText = currentOpt?.cookieText ?: "",
            filePath = currentOpt?.cookieFilePath ?: "",
            onDismiss = { showCookieDialog = false },
            onSaveCookieText = { newText ->
                viewModel.saveCookieText(selectedSiteId, newText)
                showCookieDialog = false
            }
        )
    }
}
