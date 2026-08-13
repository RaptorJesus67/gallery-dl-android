package com.kcmitch.gallery_dl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.data.SiteOptions
import com.kcmitch.gallery_dl.data.SupportedSitesData

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SiteOptionsCard(
    siteId: String,
    options: SiteOptions,
    onOpenLoginSettings: () -> Unit,
    onUserInputChange: (String) -> Unit,
    onToggleInclude: (String) -> Unit,
    onMoveIncludeUp: (String) -> Unit = {},
    onMoveIncludeDown: (String) -> Unit = {},
    onTaggedFilterModeChange: (String) -> Unit = {},
    onTaggedUsersInputChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val site = SupportedSitesData.getSiteById(siteId) ?: return
    var expandedTaggedDropdown by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("site_options_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "${site.name} Extractor Options",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "Config JSON",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1. LOGIN SETTINGS (Firefox cookies file)
            Surface(
                shape = RoundedCornerShape(14.dp),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = "Login",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Login / Session Cookies",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (options.cookieText.isNotBlank())
                                    "App Data file: cookies/${siteId.lowercase()}.txt (Active)"
                                else
                                    "App Data file: cookies/${siteId.lowercase()}.txt (Empty)",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (options.cookieText.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Button(
                        onClick = onOpenLoginSettings,
                        modifier = Modifier.testTag("login_settings_button"),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Login settings...")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. USER TEXT FIELD
            Text(
                text = "Target Username(s)",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = options.usersInput,
                onValueChange = onUserInputChange,
                placeholder = { Text("Enter names separated by comma (e.g., creator_1, creator_2)") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("users_input_field"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 3. REORDERABLE INCLUDES SECTION (Media type/priority container)
            ReorderableMediaTypesMenu(
                site = site,
                selectedIncludes = options.selectedIncludes,
                includeOrder = options.includeOrder,
                onToggleInclude = onToggleInclude,
                onMoveUp = onMoveIncludeUp,
                onMoveDown = onMoveIncludeDown
            )

            // 4. INSTAGRAM TAGGED MEDIA DROPDOWN & FILTER BOX (Positioned below media types container)
            if (siteId == "instagram") {
                Spacer(modifier = Modifier.height(18.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Sell,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Tagged Media Filter:",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Dropdown trigger
                            Box {
                                OutlinedButton(
                                    onClick = { expandedTaggedDropdown = true },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.testTag("tagged_filter_dropdown_button")
                                ) {
                                    Text(
                                        text = if (options.taggedFilterMode == "users") "Filter by tagged users" else "all tagged images",
                                        fontWeight = FontWeight.Medium,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                                }

                                DropdownMenu(
                                    expanded = expandedTaggedDropdown,
                                    onDismissRequest = { expandedTaggedDropdown = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("all tagged images") },
                                        onClick = {
                                            onTaggedFilterModeChange("all")
                                            expandedTaggedDropdown = false
                                        },
                                        leadingIcon = {
                                            if (options.taggedFilterMode == "all") {
                                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                            }
                                        },
                                        modifier = Modifier.testTag("tagged_option_all")
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Filter by tagged users...") },
                                        onClick = {
                                            onTaggedFilterModeChange("users")
                                            expandedTaggedDropdown = false
                                        },
                                        leadingIcon = {
                                            if (options.taggedFilterMode == "users") {
                                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                            }
                                        },
                                        modifier = Modifier.testTag("tagged_option_users")
                                    )
                                }
                            }
                        }

                        // Expandable Box when "Filter by tagged users" is selected
                        if (options.taggedFilterMode == "users") {
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Add users to filter tagged images from:",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    OutlinedTextField(
                                        value = options.taggedUsersInput,
                                        onValueChange = onTaggedUsersInputChange,
                                        placeholder = { Text("e.g. user_a, photographer_b") },
                                        leadingIcon = {
                                            Icon(imageVector = Icons.Default.FilterList, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("tagged_users_input_field"),
                                        singleLine = true
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Applies Python expression: --filter \"username in ('user_a', 'photographer_b')\"",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.primary
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
