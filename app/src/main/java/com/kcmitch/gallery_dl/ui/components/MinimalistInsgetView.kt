package com.kcmitch.gallery_dl.ui.components

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.data.SiteOptions
import com.kcmitch.gallery_dl.data.SupportedSitesData
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinimalistInsgetView(
    viewModel: GalleryDlViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedSiteId by viewModel.selectedSiteId.collectAsState()
    val siteOptionsMap by viewModel.siteOptionsMap.collectAsState()
    val isDownloading by viewModel.isDownloading.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()
    val downloadLogs by viewModel.downloadLogs.collectAsState()

    val site = SupportedSitesData.getSiteById(selectedSiteId) ?: SupportedSitesData.allSites.first()
    val currentOptions = siteOptionsMap[selectedSiteId] ?: SiteOptions(siteId = selectedSiteId)

    var linkInput by remember(currentOptions.usersInput) { mutableStateOf(currentOptions.usersInput) }
    var showAdvancedOptions by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // -------------------------------------------------------------
        // CONTAINER BLOCK 1: Insget-Style Prominent Link Paste Box
        // -------------------------------------------------------------
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("insget_link_card")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = site.emojiIcon, fontSize = 20.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "${site.name} Downloader",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Paste link or handle to fetch high-res media",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Auto Paste Clipboard Button
                    FilledTonalButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                            val clipData = clipboard?.primaryClip
                            if (clipData != null && clipData.itemCount > 0) {
                                val text = clipData.getItemAt(0).text?.toString() ?: ""
                                if (text.isNotBlank()) {
                                    linkInput = text
                                    viewModel.updateUsersInput(selectedSiteId, text)
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("insget_paste_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentPaste,
                            contentDescription = "Paste",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Paste", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Text Field Input
                OutlinedTextField(
                    value = linkInput,
                    onValueChange = {
                        linkInput = it
                        viewModel.updateUsersInput(selectedSiteId, it)
                    },
                    placeholder = { Text("Paste ${site.name} post, reel, story or profile URL...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("insget_link_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Primary 1-Tap Download Button
                Button(
                    onClick = { viewModel.runDownloadSimulation() },
                    enabled = !isDownloading,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("insget_download_button")
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isDownloading) "Downloading Media..." else "Download Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                if (isDownloading) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Extracting media files...", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "${(downloadProgress * 100).toInt()}%",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { downloadProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // PROGRESSIVE DISCLOSURE TOGGLE: "Tack on More Options"
        // -------------------------------------------------------------
        OutlinedButton(
            onClick = { showAdvancedOptions = !showAdvancedOptions },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("toggle_tack_on_options_button")
        ) {
            Icon(
                imageVector = if (showAdvancedOptions) Icons.Default.ExpandLess else Icons.Default.Tune,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (showAdvancedOptions) "Hide Advanced Controls" else "➕ Customize Media Types & Download Priority Order",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }

        // -------------------------------------------------------------
        // TACK-ON ADVANCED CONTAINER BLOCKS
        // -------------------------------------------------------------
        AnimatedVisibility(
            visible = showAdvancedOptions,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // CONTAINER BLOCK 2: Reorderable Media Types & Download Priority Order
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("insget_media_types_container")
                ) {
                    Box(modifier = Modifier.padding(18.dp)) {
                        ReorderableMediaTypesMenu(
                            site = site,
                            selectedIncludes = currentOptions.selectedIncludes,
                            includeOrder = currentOptions.includeOrder,
                            onToggleInclude = { viewModel.toggleIncludeOption(selectedSiteId, it) },
                            onMoveUp = { viewModel.moveIncludeOptionUp(selectedSiteId, it) },
                            onMoveDown = { viewModel.moveIncludeOptionDown(selectedSiteId, it) }
                        )
                    }
                }

                // CONTAINER BLOCK 3: Session Cookies / Login Container Block
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("insget_login_container")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = if (currentOptions.cookieText.isNotBlank()) Icons.Default.Key else Icons.Default.Lock,
                                contentDescription = "Cookies",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Account Session Cookies",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (currentOptions.cookieText.isNotBlank())
                                        "Connected: cookies/${selectedSiteId.lowercase()}.txt"
                                    else
                                        "Optional: Add cookies to download private/HD media",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.openCookieDialog(selectedSiteId) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("insget_login_button")
                        ) {
                            Text(if (currentOptions.cookieText.isNotBlank()) "Manage" else "Login")
                        }
                    }
                }

                // CONTAINER BLOCK 4: CLI Preview Terminal
                CommandPreviewTerminal(
                    commandString = viewModel.buildCliCommand(),
                    configJson = viewModel.configJsonContent.collectAsState().value,
                    isDownloading = isDownloading,
                    downloadProgress = downloadProgress,
                    downloadLogs = downloadLogs,
                    onRunDownload = { viewModel.runDownloadSimulation() },
                    onClearLogs = { viewModel.clearLogs() }
                )
            }
        }
    }
}
