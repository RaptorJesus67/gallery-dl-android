package com.kcmitch.gallery_dl.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.data.DownloadLogEntry
import com.kcmitch.gallery_dl.data.LogType

@Composable
fun CommandPreviewTerminal(
    commandString: String,
    configJson: String,
    isDownloading: Boolean,
    downloadProgress: Float,
    downloadLogs: List<DownloadLogEntry>,
    verboseFlag: Boolean = false,
    simulateFlag: Boolean = false,
    onToggleVerbose: () -> Unit = {},
    onToggleSimulate: () -> Unit = {},
    onRunDownload: () -> Unit,
    onClearLogs: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showConfigView by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("command_terminal_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F172A) // Dark terminal slate
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = "Terminal",
                        tint = Color(0xFF38BDF8)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "gallery-dl CLI Command",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("gallery-dl command", commandString)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Command copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("copy_command_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Command",
                            tint = Color(0xFF94A3B8)
                        )
                    }

                    IconButton(
                        onClick = { showConfigView = !showConfigView },
                        modifier = Modifier.testTag("toggle_config_json_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Toggle Config JSON",
                            tint = if (showConfigView) Color(0xFF38BDF8) else Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Checkboxes for App Debugging: Verbose (-v) & Simulate (-s)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag("checkbox_verbose_row")
                ) {
                    Checkbox(
                        checked = verboseFlag,
                        onCheckedChange = { onToggleVerbose() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF38BDF8),
                            uncheckedColor = Color(0xFF64748B)
                        ),
                        modifier = Modifier.testTag("checkbox_verbose")
                    )
                    Text(
                        text = "Verbose (-v)",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag("checkbox_simulate_row")
                ) {
                    Checkbox(
                        checked = simulateFlag,
                        onCheckedChange = { onToggleSimulate() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF38BDF8),
                            uncheckedColor = Color(0xFF64748B)
                        ),
                        modifier = Modifier.testTag("checkbox_simulate")
                    )
                    Text(
                        text = "Simulate (-s)",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Command Box
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = commandString,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .testTag("command_text_box"),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = Color(0xFF38BDF8)
                )
            }

            if (showConfigView) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Generated config.json (App Data):",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF1E293B),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = configJson,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .testTag("config_json_box"),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Color(0xFFA7F3D0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Run Download Action Button
            Button(
                onClick = onRunDownload,
                enabled = !isDownloading,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("run_download_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4338CA),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isDownloading) "Executing gallery-dl..." else "Run gallery-dl Downloader",
                    fontWeight = FontWeight.Bold
                )
            }

            if (isDownloading || downloadLogs.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))

                if (isDownloading) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Crawling & Downloading Media...",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFE2E8F0)
                            )
                            Text(
                                text = "${(downloadProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { downloadProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),
                            color = Color(0xFF38BDF8),
                            trackColor = Color(0xFF334155)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Terminal Output Console
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Console Output Logs:",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF94A3B8)
                    )
                    if (downloadLogs.isNotEmpty() && !isDownloading) {
                        IconButton(
                            onClick = onClearLogs,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear logs",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF020617),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(downloadLogs) { entry ->
                            val color = when (entry.type) {
                                LogType.SUCCESS -> Color(0xFF4ADE80)
                                LogType.WARNING -> Color(0xFFFACC15)
                                LogType.ERROR -> Color(0xFFF87171)
                                LogType.PROGRESS -> Color(0xFF38BDF8)
                                LogType.INFO -> Color(0xFFCBD5E1)
                            }

                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "[${entry.timestamp}] ",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    color = Color(0xFF64748B)
                                )
                                Text(
                                    text = entry.message,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    color = color
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
