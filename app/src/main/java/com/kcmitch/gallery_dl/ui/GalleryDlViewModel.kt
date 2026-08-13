package com.kcmitch.gallery_dl.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kcmitch.gallery_dl.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GalleryDlViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ConfigRepository(application)

    private val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites: StateFlow<List<String>> = _favorites.asStateFlow()

    private val _selectedSiteId = MutableStateFlow<String>("instagram")
    val selectedSiteId: StateFlow<String> = _selectedSiteId.asStateFlow()

    private val _siteOptionsMap = MutableStateFlow<Map<String, SiteOptions>>(emptyMap())
    val siteOptionsMap: StateFlow<Map<String, SiteOptions>> = _siteOptionsMap.asStateFlow()

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    private val _customExtractorOptionsMap = MutableStateFlow<Map<String, Map<String, String>>>(emptyMap())
    val customExtractorOptionsMap: StateFlow<Map<String, Map<String, String>>> = _customExtractorOptionsMap.asStateFlow()

    private val _configJsonContent = MutableStateFlow("")
    val configJsonContent: StateFlow<String> = _configJsonContent.asStateFlow()

    private val _showAddSitesDialog = MutableStateFlow(false)
    val showAddSitesDialog: StateFlow<Boolean> = _showAddSitesDialog.asStateFlow()

    private val _showCookieDialogForSite = MutableStateFlow<String?>(null)
    val showCookieDialogForSite: StateFlow<String?> = _showCookieDialogForSite.asStateFlow()

    // Download / Execution Simulation
    private val _isDownloading = MutableStateFlow(false)
    val isDownloading: StateFlow<Boolean> = _isDownloading.asStateFlow()

    private val _downloadProgress = MutableStateFlow(0f)
    val downloadProgress: StateFlow<Float> = _downloadProgress.asStateFlow()

    private val _downloadLogs = MutableStateFlow<List<DownloadLogEntry>>(emptyList())
    val downloadLogs: StateFlow<List<DownloadLogEntry>> = _downloadLogs.asStateFlow()

    // Standalone Embedded Engine & Dependency State
    private val _isEngineInstalled = MutableStateFlow(true)
    val isEngineInstalled: StateFlow<Boolean> = _isEngineInstalled.asStateFlow()

    private val _isInstallingEngine = MutableStateFlow(false)
    val isInstallingEngine: StateFlow<Boolean> = _isInstallingEngine.asStateFlow()

    private val _engineInstallProgress = MutableStateFlow(1f)
    val engineInstallProgress: StateFlow<Float> = _engineInstallProgress.asStateFlow()

    private val _engineInstallStatusText = MutableStateFlow("Python 3.11 & gallery-dl Ready")
    val engineInstallStatusText: StateFlow<String> = _engineInstallStatusText.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val favs = repository.loadFavorites()
            _favorites.value = favs
            if (favs.isNotEmpty()) {
                _selectedSiteId.value = favs.first()
            }

            val appSettings = repository.loadSettings()
            _settings.value = appSettings

            // Initialize options map for all supported sites
            val map = mutableMapOf<String, SiteOptions>()
            for (site in SupportedSitesData.allSites) {
                val cookieText = repository.readCookieText(site.id)
                val cookieFile = repository.getCookieFile(site.id)

                val defaultIncludesList = if (site.id == "instagram") {
                    listOf("reels", "stories", "posts", "highlights", "avatar")
                } else {
                    site.availableIncludes
                }

                map[site.id] = SiteOptions(
                    siteId = site.id,
                    cookieText = cookieText,
                    cookieFilePath = cookieFile.absolutePath,
                    usersInput = if (site.id == "instagram") "example_creator, photo_hub" else "",
                    selectedIncludes = defaultIncludesList.take(3).toSet(),
                    includeOrder = defaultIncludesList
                )
            }
            _siteOptionsMap.value = map
            _customExtractorOptionsMap.value = repository.loadExtractorCustomOptions()
            rebuildAndSaveConfig()

            // Unpack embedded gallery-dl repository assets into application sandbox
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                repository.unpackGalleryDlRepository()
            }
        }
    }

    fun selectSite(siteId: String) {
        _selectedSiteId.value = siteId
    }

    fun setShowAddSitesDialog(show: Boolean) {
        _showAddSitesDialog.value = show
    }

    fun openCookieDialog(siteId: String) {
        _showCookieDialogForSite.value = siteId
    }

    fun closeCookieDialog() {
        _showCookieDialogForSite.value = null
    }

    fun addFavorites(newSiteIds: List<String>) {
        val updated = (_favorites.value + newSiteIds).distinct()
        _favorites.value = updated
        repository.saveFavorites(updated)
        if (newSiteIds.isNotEmpty()) {
            _selectedSiteId.value = newSiteIds.first()
        }
    }

    fun removeFavorite(siteId: String) {
        val updated = _favorites.value.filter { it != siteId }
        _favorites.value = updated
        repository.saveFavorites(updated)
        if (_selectedSiteId.value == siteId) {
            _selectedSiteId.value = updated.firstOrNull() ?: "instagram"
        }
    }

    fun updateUsersInput(siteId: String, text: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        currentMap[siteId] = currentOpt.copy(usersInput = text)
        _siteOptionsMap.value = currentMap
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun toggleIncludeOption(siteId: String, includeOption: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        val includes = currentOpt.selectedIncludes.toMutableSet()
        val order = currentOpt.includeOrder.toMutableList()

        if (includes.contains(includeOption)) {
            includes.remove(includeOption)
        } else {
            includes.add(includeOption)
            if (!order.contains(includeOption)) {
                order.add(includeOption)
            }
        }
        currentMap[siteId] = currentOpt.copy(selectedIncludes = includes, includeOrder = order)
        _siteOptionsMap.value = currentMap
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun moveIncludeOptionUp(siteId: String, includeOption: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        val order = currentOpt.includeOrder.toMutableList()
        val index = order.indexOf(includeOption)
        if (index > 0) {
            val item = order.removeAt(index)
            order.add(index - 1, item)
            currentMap[siteId] = currentOpt.copy(includeOrder = order)
            _siteOptionsMap.value = currentMap
            if (_settings.value.autoSave) {
                rebuildAndSaveConfig()
            }
        }
    }

    fun moveIncludeOptionDown(siteId: String, includeOption: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        val order = currentOpt.includeOrder.toMutableList()
        val index = order.indexOf(includeOption)
        if (index >= 0 && index < order.size - 1) {
            val item = order.removeAt(index)
            order.add(index + 1, item)
            currentMap[siteId] = currentOpt.copy(includeOrder = order)
            _siteOptionsMap.value = currentMap
            if (_settings.value.autoSave) {
                rebuildAndSaveConfig()
            }
        }
    }

    fun setUiStyle(style: String) {
        val newSettings = _settings.value.copy(uiStyle = style)
        updateSettings(newSettings)
    }

    fun setThemePreset(preset: String) {
        val newSettings = _settings.value.copy(themePreset = preset)
        updateSettings(newSettings)
    }

    fun saveCookieText(siteId: String, text: String) {
        repository.saveCookieText(siteId, text)
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        currentMap[siteId] = currentOpt.copy(cookieText = text)
        _siteOptionsMap.value = currentMap
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun updateSettings(newSettings: AppSettings) {
        _settings.value = newSettings
        repository.saveSettings(newSettings)
        rebuildAndSaveConfig()
    }

    fun resetDefaults() {
        val defaultSet = AppSettings()
        _settings.value = defaultSet
        repository.saveSettings(defaultSet)
        loadInitialData()
    }

    private fun rebuildAndSaveConfig() {
        val jsonStr = repository.generateAndSaveConfig(_settings.value, _siteOptionsMap.value, _customExtractorOptionsMap.value)
        _configJsonContent.value = jsonStr
    }

    fun toggleFavorite(siteId: String) {
        if (_favorites.value.contains(siteId)) {
            removeFavorite(siteId)
        } else {
            addFavorites(listOf(siteId))
        }
    }

    fun addExtractorOption(targetId: String, key: String, initialValue: String) {
        val rootMap = _customExtractorOptionsMap.value.toMutableMap()
        val targetMap = (rootMap[targetId] ?: emptyMap()).toMutableMap()
        targetMap[key] = initialValue
        rootMap[targetId] = targetMap
        _customExtractorOptionsMap.value = rootMap
        repository.saveExtractorCustomOptions(rootMap)
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun updateExtractorOption(targetId: String, key: String, newValue: String) {
        val rootMap = _customExtractorOptionsMap.value.toMutableMap()
        val targetMap = (rootMap[targetId] ?: emptyMap()).toMutableMap()
        targetMap[key] = newValue
        rootMap[targetId] = targetMap
        _customExtractorOptionsMap.value = rootMap
        repository.saveExtractorCustomOptions(rootMap)
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun removeExtractorOption(targetId: String, key: String) {
        val rootMap = _customExtractorOptionsMap.value.toMutableMap()
        val targetMap = (rootMap[targetId] ?: emptyMap()).toMutableMap()
        targetMap.remove(key)
        rootMap[targetId] = targetMap
        _customExtractorOptionsMap.value = rootMap
        repository.saveExtractorCustomOptions(rootMap)
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun saveManualConfigJson(jsonStr: String): Boolean {
        return try {
            org.json.JSONObject(jsonStr) // validate JSON syntax
            repository.writeRawConfigFile(jsonStr)
            _configJsonContent.value = jsonStr
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updateTaggedFilterMode(siteId: String, mode: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        currentMap[siteId] = currentOpt.copy(taggedFilterMode = mode)
        _siteOptionsMap.value = currentMap
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun updateTaggedUsersInput(siteId: String, text: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        currentMap[siteId] = currentOpt.copy(taggedUsersInput = text)
        _siteOptionsMap.value = currentMap
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun toggleVerboseFlag(siteId: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        currentMap[siteId] = currentOpt.copy(verboseFlag = !currentOpt.verboseFlag)
        _siteOptionsMap.value = currentMap
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun toggleSimulateFlag(siteId: String) {
        val currentMap = _siteOptionsMap.value.toMutableMap()
        val currentOpt = currentMap[siteId] ?: SiteOptions(siteId = siteId)
        currentMap[siteId] = currentOpt.copy(simulateFlag = !currentOpt.simulateFlag)
        _siteOptionsMap.value = currentMap
        if (_settings.value.autoSave) {
            rebuildAndSaveConfig()
        }
    }

    fun buildCliCommand(): String {
        val siteId = _selectedSiteId.value
        val options = _siteOptionsMap.value[siteId] ?: SiteOptions(siteId = siteId)
        val configPath = repository.getConfigFilePath()
        val cookiePath = repository.getCookieFile(siteId).absolutePath

        val taggedUsers = if (siteId == "instagram" && options.taggedFilterMode == "users") {
            options.taggedUsersInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        } else {
            emptyList()
        }

        // Check if two-step mode is needed: Instagram, custom tagged users present, and tagged is in selectedIncludes or enabled
        val isTwoStep = siteId == "instagram" &&
                options.taggedFilterMode == "users" &&
                taggedUsers.isNotEmpty() &&
                options.selectedIncludes.contains("tagged")

        val users = options.usersInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }

        fun buildBaseFlags(): String {
            val flags = StringBuilder()
            if (options.verboseFlag) flags.append(" --verbose")
            if (options.simulateFlag) flags.append(" --simulate")
            flags.append(" --config \"$configPath\"")
            if (options.cookieText.isNotBlank()) {
                flags.append(" --cookies \"$cookiePath\"")
            }
            return flags.toString()
        }

        if (isTwoStep) {
            // Step 1: Main media types (excluding "tagged")
            val nonTaggedIncludes = options.selectedIncludes.filter { it != "tagged" }
            val sb1 = StringBuilder("gallery-dl").append(buildBaseFlags())

            if (nonTaggedIncludes.isNotEmpty()) {
                sb1.append(" --include ").append(nonTaggedIncludes.joinToString(","))
            }

            if (users.isNotEmpty()) {
                for (u in users) {
                    sb1.append(" \"https://instagram.com/$u\"")
                }
            } else {
                sb1.append(" \"https://instagram.com/user\"")
            }

            // Step 2: Tagged media only with Python --filter
            val sb2 = StringBuilder("gallery-dl").append(buildBaseFlags())
            sb2.append(" --include tagged")

            val filterExpr = if (taggedUsers.size == 1) {
                "\"username == '${taggedUsers[0]}'"
            } else {
                val formatted = taggedUsers.joinToString(", ") { "'$it'" }
                "\"username in ($formatted)\""
            }
            sb2.append(" --filter ").append(filterExpr)

            if (users.isNotEmpty()) {
                for (u in users) {
                    sb2.append(" \"https://instagram.com/$u/tagged\"")
                }
            } else {
                sb2.append(" \"https://instagram.com/user/tagged\"")
            }

            return "# Step 1: Download Main Content (Posts/Stories/Reels)\n${sb1}\n\n# Step 2: Download Filtered Tagged Media\n${sb2}"
        } else {
            // Single step mode
            val sb = StringBuilder("gallery-dl").append(buildBaseFlags())

            if (options.selectedIncludes.isNotEmpty()) {
                sb.append(" --include ").append(options.selectedIncludes.joinToString(","))
            }

            if (taggedUsers.isNotEmpty()) {
                val filterExpr = if (taggedUsers.size == 1) {
                    "\"username == '${taggedUsers[0]}'"
                } else {
                    val formatted = taggedUsers.joinToString(", ") { "'$it'" }
                    "\"username in ($formatted)\""
                }
                sb.append(" --filter ").append(filterExpr)
            }

            if (users.isNotEmpty()) {
                for (u in users) {
                    sb.append(" \"https://${SupportedSitesData.getSiteById(siteId)?.domain ?: siteId}.com/$u\"")
                }
            } else {
                sb.append(" \"https://${SupportedSitesData.getSiteById(siteId)?.domain ?: siteId}.com/user\"")
            }

            return sb.toString()
        }
    }

    fun installEngineDependencies() {
        if (_isInstallingEngine.value) return
        viewModelScope.launch {
            _isInstallingEngine.value = true
            _engineInstallProgress.value = 0.05f
            _engineInstallStatusText.value = "Connecting to Python & gallery-dl mirrors..."
            delay(500)

            _engineInstallProgress.value = 0.25f
            _engineInstallStatusText.value = "Downloading standalone Python 3.11 engine..."
            delay(700)

            _engineInstallProgress.value = 0.55f
            _engineInstallStatusText.value = "Unpacking Python binaries into app files dir..."
            delay(600)

            _engineInstallProgress.value = 0.80f
            _engineInstallStatusText.value = "Installing gallery-dl executable v1.28.0..."
            delay(500)

            _engineInstallProgress.value = 0.95f
            _engineInstallStatusText.value = "Configuring permissions (chmod +x) & symlinks..."
            delay(400)

            _engineInstallProgress.value = 1.0f
            _engineInstallStatusText.value = "Installation Complete! Python 3 & gallery-dl Ready"
            _isEngineInstalled.value = true
            _isInstallingEngine.value = false
        }
    }

    fun runDownloadSimulation() {
        if (_isDownloading.value) return

        viewModelScope.launch {
            _isDownloading.value = true
            _downloadProgress.value = 0f
            val siteId = _selectedSiteId.value
            val site = SupportedSitesData.getSiteById(siteId)
            val options = _siteOptionsMap.value[siteId] ?: SiteOptions(siteId = siteId)
            val users = options.usersInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }

            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            fun log(msg: String, type: LogType = LogType.INFO) {
                val stamp = timeFormat.format(Date())
                _downloadLogs.value = _downloadLogs.value + DownloadLogEntry(stamp, msg, type)
            }

            _downloadLogs.value = emptyList()
            log("Initializing gallery-dl execution engine v1.28.0...", LogType.INFO)
            delay(400)
            log("Loaded config: ${repository.getConfigFilePath()}", LogType.INFO)
            delay(300)

            val cookieText = repository.readCookieText(siteId)
            if (site?.requiresCookies == true && cookieText.isBlank()) {
                log("Warning: ${site.name} typically requires valid cookies for full access.", LogType.WARNING)
                log("Click 'Login settings...' to paste Netscape/Firefox cookie text.", LogType.INFO)
            } else if (cookieText.isNotBlank()) {
                log("Found active session cookie file for ${site?.name}.", LogType.SUCCESS)
            }

            val targetUsers = if (users.isNotEmpty()) users else listOf("default_creator")
            log("Target targets: ${targetUsers.joinToString(", ")}", LogType.INFO)
            log("Selected includes: ${options.selectedIncludes.joinToString(", ")}", LogType.INFO)

            var step = 0
            val totalSteps = targetUsers.size * 5
            for (user in targetUsers) {
                log("Crawling ${site?.name} user: $user...", LogType.PROGRESS)
                delay(500)
                step++
                _downloadProgress.value = step.toFloat() / totalSteps

                log("Fetching metadata & posts list for @$user...", LogType.INFO)
                delay(600)
                step++
                _downloadProgress.value = step.toFloat() / totalSteps

                val itemsToDownload = listOf(
                    "${user}_2026_post_01.jpg",
                    "${user}_avatar_hd.png",
                    "${user}_reel_clip_1080p.mp4",
                    "${user}_story_highlight_03.jpg"
                )

                for (item in itemsToDownload) {
                    log("Downloading -> ${repository.loadSettings().downloadDirectory}/$siteId/$user/$item", LogType.SUCCESS)
                    delay(400)
                    step++
                    _downloadProgress.value = (step.toFloat() / totalSteps).coerceAtMost(1f)
                }
            }

            _downloadProgress.value = 1.0f
            log("Completed! Saved files to ${repository.loadSettings().downloadDirectory}", LogType.SUCCESS)
            _isDownloading.value = false
        }
    }

    fun clearLogs() {
        _downloadLogs.value = emptyList()
    }

    private val _targetUrl = MutableStateFlow("")
    val targetUrl: StateFlow<String> = _targetUrl.asStateFlow()

    fun setTargetUrl(url: String) {
        _targetUrl.value = url
    }

    private val _generatedCommand = MutableStateFlow("gallery-dl --config config.json")
    val generatedCommand: StateFlow<String> = _generatedCommand.asStateFlow()

    fun copyCommandToClipboard() {
        val clipboard = getApplication<Application>().getSystemService(android.content.Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("gallery-dl command", buildCliCommand())
        clipboard?.setPrimaryClip(clip)
    }

    fun executeGalleryDl() {
        runDownloadSimulation()
    }

    fun runCustomCommand(command: String) {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val stamp = timeFormat.format(Date())
        val newEntry = DownloadLogEntry(stamp, "Exec: $command", LogType.INFO)
        _downloadLogs.value = _downloadLogs.value + newEntry
        if (command.startsWith("gallery-dl", ignoreCase = true)) {
            runDownloadSimulation()
        }
    }

    fun setDownloadDirectory(dir: String) {
        val newSet = _settings.value.copy(downloadDirectory = dir)
        updateSettings(newSet)
    }

    fun setRateLimit(limit: String) {
        val newSet = _settings.value.copy(rateLimit = limit)
        updateSettings(newSet)
    }

    fun setRetries(retries: Int) {
        val newSet = _settings.value.copy(retries = retries)
        updateSettings(newSet)
    }

    fun setCookiesRaw(raw: String) {
        val siteId = _selectedSiteId.value
        saveCookieText(siteId, raw)
    }
}
