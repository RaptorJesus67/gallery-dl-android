package com.kcmitch.gallery_dl.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.kcmitch.gallery_dl.data.LogType
import com.kcmitch.gallery_dl.data.SiteOptions
import com.kcmitch.gallery_dl.data.SupportedSite
import com.kcmitch.gallery_dl.data.SupportedSitesData
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreatorStudioView(
    viewModel: GalleryDlViewModel,
    modifier: Modifier = Modifier
) {
    val selectedSiteId by viewModel.selectedSiteId.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val siteOptionsMap by viewModel.siteOptionsMap.collectAsState()
    val isDownloading by viewModel.isDownloading.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()
    val downloadLogs by viewModel.downloadLogs.collectAsState()

    val site = SupportedSitesData.getSiteById(selectedSiteId) ?: SupportedSitesData.allSites.first()
    val currentOptions = siteOptionsMap[selectedSiteId] ?: SiteOptions(siteId = selectedSiteId)

    val brandColor = Color(site.brandColorHex)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Social Platform Selector Bar
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Platform",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                TextButton(
                    onClick = { viewModel.setShowAddSitesDialog(true) },
                    modifier = Modifier.testTag("creator_add_platform_button")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Platform", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("creator_platform_row")
            ) {
                items(favorites) { favId ->
                    val favSite = SupportedSitesData.getSiteById(favId) ?: return@items
                    val isSelected = favId == selectedSiteId
                    val favColor = Color(favSite.brandColorHex)

                    Surface(
                        onClick = { viewModel.selectSite(favId) },
                        shape = RoundedCornerShape(18.dp),
                        color = if (isSelected) favColor else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        tonalElevation = if (isSelected) 6.dp else 0.dp,
                        modifier = Modifier.testTag("creator_platform_chip_$favId")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = favSite.emojiIcon, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = favSite.name,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        // 2. Main Creator Download Card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("creator_studio_card")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header with Creator Profile Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(brandColor, brandColor.copy(alpha = 0.6f))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = site.emojiIcon, fontSize = 26.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${site.name} Content Saver",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = brandColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = site.category,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = brandColor,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = site.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Target Usernames Input Box
                Text(
                    text = "Target Creator Username or Handle:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = currentOptions.usersInput,
                    onValueChange = { viewModel.updateUsersInput(selectedSiteId, it) },
                    placeholder = { Text("e.g. creator_one, @tiktok_star") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AlternateEmail,
                            contentDescription = "User",
                            tint = brandColor
                        )
                    },
                    trailingIcon = {
                        if (currentOptions.usersInput.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateUsersInput(selectedSiteId, "") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("creator_users_input"),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Content Types Multi-Select Chips
                Text(
                    text = "Media Types to Download:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    site.availableIncludes.forEach { option ->
                        val isSelected = currentOptions.selectedIncludes.contains(option)

                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.toggleIncludeOption(selectedSiteId, option) },
                            label = {
                                Text(
                                    text = when (option) {
                                        "avatar" -> "👤 Profile Avatar"
                                        "posts" -> "📸 Posts & Feed"
                                        "stories" -> "⭕ Stories"
                                        "reels", "videos" -> "🎬 Reels & Videos"
                                        "highlights" -> "✨ Highlights"
                                        "tagged" -> "🏷️ Tagged Media"
                                        "tweets" -> "🐦 Tweets"
                                        "likes" -> "❤️ Likes"
                                        "bookmarks" -> "🔖 Bookmarks"
                                        else -> option.replaceFirstChar { it.uppercase() }
                                    },
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            leadingIcon = if (isSelected) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = brandColor.copy(alpha = 0.2f),
                                selectedLabelColor = brandColor
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("creator_chip_$option")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Login Session Status Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(
                                imageVector = if (currentOptions.cookieText.isNotBlank()) Icons.Default.VerifiedUser else Icons.Default.Lock,
                                contentDescription = null,
                                tint = if (currentOptions.cookieText.isNotBlank()) Color(0xFF10B981) else MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (currentOptions.cookieText.isNotBlank()) "Account Session Connected" else "Session Cookies Empty",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (currentOptions.cookieText.isNotBlank())
                                        "App Data cookie file configured for ${site.name}."
                                    else
                                        "Required for downloading private or high-res content.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.openCookieDialog(selectedSiteId) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = brandColor),
                            modifier = Modifier.testTag("creator_login_settings_button")
                        ) {
                            Text(if (currentOptions.cookieText.isNotBlank()) "Manage" else "Login")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Big Save Button
                Button(
                    onClick = { viewModel.runDownloadSimulation() },
                    enabled = !isDownloading,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = brandColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("creator_start_download_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDownload,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isDownloading) "Saving ${site.name} Media..." else "Save ${site.name} Content Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                if (isDownloading) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Downloading posts & stories...", style = MaterialTheme.typography.bodySmall)
                            Text(text = "${(downloadProgress * 100).toInt()}%", fontWeight = FontWeight.Bold, color = brandColor)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { downloadProgress },
                            color = brandColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
                    }
                }
            }
        }

        // Recent Download Activity Console Log Card
        if (downloadLogs.isNotEmpty()) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null, tint = Color(0xFF10B981))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Recent Activity Logs", fontWeight = FontWeight.Bold)
                        }
                        IconButton(onClick = { viewModel.clearLogs() }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    downloadLogs.takeLast(4).forEach { log ->
                        Text(
                            text = "• ${log.message}",
                            style = MaterialTheme.typography.bodySmall,
                            color = when (log.type) {
                                LogType.SUCCESS -> Color(0xFF10B981)
                                LogType.WARNING -> Color(0xFFF59E0B)
                                LogType.ERROR -> MaterialTheme.colorScheme.error
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
