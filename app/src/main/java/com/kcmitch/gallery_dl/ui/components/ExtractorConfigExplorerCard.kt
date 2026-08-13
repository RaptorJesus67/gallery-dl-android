package com.kcmitch.gallery_dl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.data.ExtractorOptionRegistry
import com.kcmitch.gallery_dl.data.ExtractorValueType
import com.kcmitch.gallery_dl.data.SupportedSitesData
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel

@Composable
fun ExtractorConfigExplorerCard(
    viewModel: GalleryDlViewModel,
    modifier: Modifier = Modifier
) {
    val favorites by viewModel.favorites.collectAsState()
    val customExtractorOptionsMap by viewModel.customExtractorOptionsMap.collectAsState()

    var selectedTargetId by remember { mutableStateOf("GLOBAL") }
    var expandedDropdown by remember { mutableStateOf(false) }
    var expandedAddMenu by remember { mutableStateOf(false) }

    // Sort sites alphabetically
    val allSitesSorted = remember {
        SupportedSitesData.allSites.sortedBy { it.name.lowercase() }
    }

    val favoriteSitesSorted = remember(favorites, allSitesSorted) {
        allSitesSorted.filter { favorites.contains(it.id) }
    }

    val otherSitesSorted = remember(favorites, allSitesSorted) {
        allSitesSorted.filter { !favorites.contains(it.id) }
    }

    val currentTargetName = if (selectedTargetId == "GLOBAL") {
        "🌐 GLOBAL (Global Extractor Defaults)"
    } else {
        val site = SupportedSitesData.getSiteById(selectedTargetId)
        "${site?.emojiIcon ?: "🌐"} ${site?.name ?: selectedTargetId}"
    }

    val currentCustomOptions = customExtractorOptionsMap[selectedTargetId] ?: emptyMap()

    // Unused options for the active target
    val unusedOptions = remember(currentCustomOptions) {
        ExtractorOptionRegistry.defaultOptions.filter { !currentCustomOptions.containsKey(it.key) }
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("extractor_config_explorer_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Section Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Extractor Configuration Explorer",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Configure site-specific & global gallery-dl extractor options. Site settings override global defaults.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Site & Global Target Selector Dropdown
            Text(
                text = "Select Site or Global Target:",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expandedDropdown = true },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("extractor_target_dropdown_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentTargetName,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }

                DropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .heightIn(max = 400.dp)
                ) {
                    // 1. GLOBAL Option at very top
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "🌐 GLOBAL (Global Extractor Defaults)",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            selectedTargetId = "GLOBAL"
                            expandedDropdown = false
                        },
                        leadingIcon = {
                            if (selectedTargetId == "GLOBAL") {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        },
                        modifier = Modifier.testTag("target_option_GLOBAL")
                    )

                    HorizontalDivider()

                    // 2. FAVORITES SECTION
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "⭐ FAVORITES (Priority)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEAB308)
                            )
                        },
                        onClick = {},
                        enabled = false
                    )

                    if (favoriteSitesSorted.isEmpty()) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "No favorites starred yet. Star a site below!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            onClick = {},
                            enabled = false
                        )
                    } else {
                        favoriteSitesSorted.forEach { site ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${site.emojiIcon} ${site.name}",
                                            fontWeight = FontWeight.Medium
                                        )
                                        IconButton(
                                            onClick = {
                                                viewModel.toggleFavorite(site.id)
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = "Starred",
                                                tint = Color(0xFFEAB308),
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    selectedTargetId = site.id
                                    expandedDropdown = false
                                },
                                leadingIcon = {
                                    if (selectedTargetId == site.id) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    }
                                },
                                modifier = Modifier.testTag("target_option_${site.id}")
                            )
                        }
                    }

                    HorizontalDivider()

                    // 3. ALL OTHER SITES SECTION
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "📱 ALL SUPPORTED SITES",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        onClick = {},
                        enabled = false
                    )

                    otherSitesSorted.forEach { site ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "${site.emojiIcon} ${site.name}",
                                        fontWeight = FontWeight.Normal
                                    )
                                    IconButton(
                                        onClick = {
                                            viewModel.toggleFavorite(site.id)
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.StarOutline,
                                            contentDescription = "Unstarred",
                                            tint = MaterialTheme.colorScheme.outline,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                selectedTargetId = site.id
                                expandedDropdown = false
                            },
                            leadingIcon = {
                                if (selectedTargetId == site.id) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                }
                            },
                            modifier = Modifier.testTag("target_option_${site.id}")
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Scope Indicator Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (selectedTargetId == "GLOBAL") {
                            "Scope: extractor.{KEY} (Global defaults applied to all extractors)"
                        } else {
                            "Scope: extractor.$selectedTargetId.{KEY} (Takes precedent over global defaults)"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Extractor Option Mini-Containers Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Active Extractor Components (${currentCustomOptions.size}):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Circular "+" Add Feature Button
                Box {
                    IconButton(
                        onClick = { expandedAddMenu = true },
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                            .testTag("add_extractor_option_fab")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Extractor Setting",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expandedAddMenu,
                        onDismissRequest = { expandedAddMenu = false },
                        modifier = Modifier.heightIn(max = 320.dp)
                    ) {
                        Text(
                            text = "Add Extractor Feature:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )

                        HorizontalDivider()

                        if (unusedOptions.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("All available features added!") },
                                onClick = {},
                                enabled = false
                            )
                        } else {
                            unusedOptions.forEach { opt ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = opt.label,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp
                                            )
                                            Text(
                                                text = "Key: ${opt.key} (Default: ${opt.defaultValue})",
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    },
                                    onClick = {
                                        viewModel.addExtractorOption(selectedTargetId, opt.key, opt.defaultValue)
                                        expandedAddMenu = false
                                    },
                                    modifier = Modifier.testTag("add_option_${opt.key}")
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (currentCustomOptions.isEmpty()) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No custom extractor settings configured for ${if (selectedTargetId == "GLOBAL") "Global" else selectedTargetId}.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap the circular '+' button above to add custom filename templates, parent directories, sleep delays, archive databases, or captions.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.sp
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    currentCustomOptions.forEach { (key, value) ->
                        val optionDef = ExtractorOptionRegistry.getDefByKey(key)

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("extractor_mini_container_$key")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = optionDef.label,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = if (selectedTargetId == "GLOBAL") "extractor.$key" else "extractor.$selectedTargetId.$key",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.removeExtractorOption(selectedTargetId, key)
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete Option",
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = optionDef.description,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = value,
                                    onValueChange = { newValue ->
                                        viewModel.updateExtractorOption(selectedTargetId, key, newValue)
                                    },
                                    placeholder = { Text(optionDef.defaultValue) },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_option_$key"),
                                    singleLine = optionDef.valueType != ExtractorValueType.JSON_ARRAY
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
