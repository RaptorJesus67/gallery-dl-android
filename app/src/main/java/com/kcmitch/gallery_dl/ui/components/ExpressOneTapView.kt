package com.kcmitch.gallery_dl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.data.SiteOptions
import com.kcmitch.gallery_dl.data.SupportedSitesData
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel

@Composable
fun ExpressOneTapView(
    viewModel: GalleryDlViewModel,
    modifier: Modifier = Modifier
) {
    val selectedSiteId by viewModel.selectedSiteId.collectAsState()
    val siteOptionsMap by viewModel.siteOptionsMap.collectAsState()
    val isDownloading by viewModel.isDownloading.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()

    val site = SupportedSitesData.getSiteById(selectedSiteId) ?: SupportedSitesData.allSites.first()
    val currentOptions = siteOptionsMap[selectedSiteId] ?: SiteOptions(siteId = selectedSiteId)

    var linkInput by remember(currentOptions.usersInput) { mutableStateOf(currentOptions.usersInput) }
    var selectedPreset by remember { mutableStateOf("all") } // "all", "stories", "reels"

    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("express_one_tap_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "⚡", fontSize = 32.sp)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "1-Tap Express Downloader",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Paste any ${site.name} profile link or handle to download high-res media instantly.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = linkInput,
                onValueChange = {
                    linkInput = it
                    viewModel.updateUsersInput(selectedSiteId, it)
                },
                placeholder = { Text("Paste username or link (e.g. instagram.com/creator)") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                },
                trailingIcon = {
                    if (linkInput.isNotEmpty()) {
                        IconButton(onClick = {
                            linkInput = ""
                            viewModel.updateUsersInput(selectedSiteId, "")
                        }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("express_link_input"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Preset Selection Pills
            Text(
                text = "Download Mode Preset:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedPreset == "all",
                    onClick = {
                        selectedPreset = "all"
                        // ensure posts & stories selected
                        viewModel.toggleIncludeOption(selectedSiteId, "posts")
                    },
                    label = { Text("✨ All HD Media") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("express_preset_all")
                )

                FilterChip(
                    selected = selectedPreset == "stories",
                    onClick = {
                        selectedPreset = "stories"
                        viewModel.toggleIncludeOption(selectedSiteId, "stories")
                    },
                    label = { Text("⭕ Stories Only") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("express_preset_stories")
                )

                FilterChip(
                    selected = selectedPreset == "reels",
                    onClick = {
                        selectedPreset = "reels"
                        viewModel.toggleIncludeOption(selectedSiteId, "reels")
                    },
                    label = { Text("🎬 Reels Only") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("express_preset_reels")
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.runDownloadSimulation() },
                enabled = !isDownloading,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("express_download_now_button")
            ) {
                Icon(imageVector = Icons.Default.Download, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isDownloading) "Downloading..." else "1-Tap Download Now",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            if (isDownloading) {
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { downloadProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        }
    }
}
