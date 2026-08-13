package com.kcmitch.gallery_dl.pages

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.components.BlankHeaderContainer
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel
import com.kcmitch.gallery_dl.ui.components.*

/**
 * Homepage (pages/Homepage.kt)
 * The default start screen. Appears clean/blank minus the bottom bar.
 * The center thumb-sized circular button adds command-building elements dynamically.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(
    viewModel: GalleryDlViewModel,
    onAddElementClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var activeElements by remember { mutableStateOf<List<String>>(emptyList()) }
    var targetUrl by remember { mutableStateOf("") }

    val settings by viewModel.settings.collectAsState()
    val selectedSiteId by viewModel.selectedSiteId.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val configJsonContent by viewModel.configJsonContent.collectAsState()
    val isDownloading by viewModel.isDownloading.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()
    val downloadLogs by viewModel.downloadLogs.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // App Header Title Container
        BlankHeaderContainer()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
        if (activeElements.isEmpty()) {
            // Clean / Blank Home Screen State with subtle watermark hint
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    IconButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier
                            .size(72.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                            .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Element",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Clean Canvas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tap the center circular button to add elements & build your gallery-dl command",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.widthIn(max = 280.dp)
                    )
                }
            }
        } else {
            // Dynamic Active Elements Builder List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    // Header Bar for Active Elements
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Command Builder Elements",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        TextButton(onClick = { activeElements = emptyList() }) {
                            Text("Clear All", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                items(activeElements) { elementType ->
                    when (elementType) {
                        "URL_INPUT" -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Target Download URL",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = targetUrl,
                                        onValueChange = {
                                            targetUrl = it
                                            viewModel.setTargetUrl(it)
                                        },
                                        placeholder = { Text("Paste URL (e.g. instagram.com/p/...)") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        trailingIcon = {
                                            IconButton(onClick = { activeElements = activeElements - elementType }) {
                                                Icon(Icons.Default.Close, contentDescription = "Remove")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                        "SITE_SELECTOR" -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Select Extractor Site", fontWeight = FontWeight.Bold)
                                        IconButton(onClick = { activeElements = activeElements - elementType }) {
                                            Icon(Icons.Default.Close, contentDescription = "Remove")
                                        }
                                    }
                                    FavoriteSiteDropdown(
                                        selectedSiteId = selectedSiteId,
                                        favoriteSiteIds = favorites,
                                        onSiteSelected = { viewModel.selectSite(it) },
                                        onRemoveFavorite = { viewModel.removeFavorite(it) },
                                        onOpenAddSitesPopup = { viewModel.setShowAddSitesDialog(true) }
                                    )
                                }
                            }
                        }
                        "COMMAND_PREVIEW" -> {
                            CommandPreviewTerminal(
                                commandString = viewModel.buildCliCommand(),
                                configJson = configJsonContent,
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
        }

        // Add Elements Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add Command Element") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Select element to insert into builder:", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                if (!activeElements.contains("URL_INPUT")) {
                                    activeElements = activeElements + "URL_INPUT"
                                }
                                showAddDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Link, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Target URL Input")
                        }

                        Button(
                            onClick = {
                                if (!activeElements.contains("SITE_SELECTOR")) {
                                    activeElements = activeElements + "SITE_SELECTOR"
                                }
                                showAddDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Language, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Extractor Site Picker")
                        }

                        Button(
                            onClick = {
                                if (!activeElements.contains("COMMAND_PREVIEW")) {
                                    activeElements = activeElements + "COMMAND_PREVIEW"
                                }
                                showAddDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Code, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Command Terminal Preview")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
}
