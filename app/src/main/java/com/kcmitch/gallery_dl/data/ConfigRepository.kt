package com.kcmitch.gallery_dl.data

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class ConfigRepository(private val context: Context) {

    private val configJsonFile: File
        get() = File(context.filesDir, "config.json")

    private val settingsFile: File
        get() = File(context.filesDir, "app_settings.json")

    private val favoritesFile: File
        get() = File(context.filesDir, "favorites.json")

    private val cookiesDir: File
        get() = File(context.filesDir, "cookies").apply {
            if (!exists()) mkdirs()
        }

    private val extractorOptionsFile: File
        get() = File(context.filesDir, "extractor_custom_options.json")

    init {
        // Ensure default cookie file for Instagram exists as requested
        getCookieFile("instagram")
    }

    fun getCookieFile(siteId: String): File {
        val file = File(cookiesDir, "${siteId.lowercase()}.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    fun readCookieText(siteId: String): String {
        return try {
            val file = getCookieFile(siteId)
            file.readText()
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error reading cookies for $siteId", e)
            ""
        }
    }

    fun saveCookieText(siteId: String, content: String) {
        try {
            val file = getCookieFile(siteId)
            file.writeText(content)
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error saving cookies for $siteId", e)
        }
    }

    fun loadFavorites(): List<String> {
        return try {
            if (!favoritesFile.exists()) {
                saveFavorites(SupportedSitesData.defaultFavorites)
                return SupportedSitesData.defaultFavorites
            }
            val jsonStr = favoritesFile.readText()
            val array = JSONArray(jsonStr)
            val list = mutableListOf<String>()
            for (i in 0 until array.length()) {
                list.add(array.getString(i))
            }
            if (list.isEmpty()) SupportedSitesData.defaultFavorites else list
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error loading favorites", e)
            SupportedSitesData.defaultFavorites
        }
    }

    fun saveFavorites(favorites: List<String>) {
        try {
            val array = JSONArray(favorites)
            favoritesFile.writeText(array.toString(2))
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error saving favorites", e)
        }
    }

    fun loadSettings(): AppSettings {
        return try {
            if (!settingsFile.exists()) {
                val defaultSettings = AppSettings()
                saveSettings(defaultSettings)
                return defaultSettings
            }
            val json = JSONObject(settingsFile.readText())
            AppSettings(
                autoSave = json.optBoolean("autoSave", true),
                themeMode = json.optString("themeMode", "light"),
                themePreset = json.optString("themePreset", "cobalt"),
                uiStyle = json.optString("uiStyle", "minimalist"),
                downloadDirectory = json.optString("downloadDirectory", "/storage/emulated/0/Download/gallery-dl"),
                rateLimit = json.optString("rateLimit", "100K"),
                retries = json.optInt("retries", 3)
            )
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error loading settings", e)
            AppSettings()
        }
    }

    fun saveSettings(settings: AppSettings) {
        try {
            val json = JSONObject().apply {
                put("autoSave", settings.autoSave)
                put("themeMode", settings.themeMode)
                put("themePreset", settings.themePreset)
                put("uiStyle", settings.uiStyle)
                put("downloadDirectory", settings.downloadDirectory)
                put("rateLimit", settings.rateLimit)
                put("retries", settings.retries)
            }
            settingsFile.writeText(json.toString(2))
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error saving settings", e)
        }
    }

    fun loadExtractorCustomOptions(): Map<String, Map<String, String>> {
        return try {
            if (!extractorOptionsFile.exists()) return emptyMap()
            val json = JSONObject(extractorOptionsFile.readText())
            val resultMap = mutableMapOf<String, Map<String, String>>()
            val keys = json.keys()
            while (keys.hasNext()) {
                val targetKey = keys.next()
                val targetObj = json.optJSONObject(targetKey)
                if (targetObj != null) {
                    val targetMap = mutableMapOf<String, String>()
                    val subKeys = targetObj.keys()
                    while (subKeys.hasNext()) {
                        val k = subKeys.next()
                        targetMap[k] = targetObj.optString(k, "")
                    }
                    resultMap[targetKey] = targetMap
                }
            }
            resultMap
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error loading custom extractor options", e)
            emptyMap()
        }
    }

    fun saveExtractorCustomOptions(optionsMap: Map<String, Map<String, String>>) {
        try {
            val root = JSONObject()
            for ((target, map) in optionsMap) {
                val targetObj = JSONObject()
                for ((key, value) in map) {
                    targetObj.put(key, value)
                }
                root.put(target, targetObj)
            }
            extractorOptionsFile.writeText(root.toString(2))
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error saving custom extractor options", e)
        }
    }

    fun generateAndSaveConfig(
        settings: AppSettings,
        siteOptionsMap: Map<String, SiteOptions>,
        customExtractorOptions: Map<String, Map<String, String>> = loadExtractorCustomOptions()
    ): String {
        val root = JSONObject()
        val extractor = JSONObject()

        extractor.put("base-directory", settings.downloadDirectory)
        if (settings.rateLimit.isNotBlank()) {
            extractor.put("rate-limit", settings.rateLimit)
        }
        extractor.put("retries", settings.retries)

        // Inject GLOBAL custom extractor settings
        val globalCustoms = customExtractorOptions["GLOBAL"] ?: emptyMap()
        for ((k, v) in globalCustoms) {
            if (v.isNotBlank()) {
                if (v.startsWith("[") && v.endsWith("]")) {
                    try {
                        extractor.put(k, JSONArray(v))
                    } catch (e: Exception) {
                        extractor.put(k, v)
                    }
                } else if (v.equals("true", ignoreCase = true) || v.equals("false", ignoreCase = true)) {
                    extractor.put(k, v.toBoolean())
                } else if (v.toIntOrNull() != null) {
                    extractor.put(k, v.toInt())
                } else {
                    extractor.put(k, v)
                }
            }
        }

        for ((siteId, options) in siteOptionsMap) {
            val siteObj = JSONObject()
            val cookieFile = getCookieFile(siteId)
            val cookieText = readCookieText(siteId)

            if (cookieText.isNotBlank()) {
                siteObj.put("cookies", cookieFile.absolutePath)
            }

            if (options.selectedIncludes.isNotEmpty()) {
                val includesArray = JSONArray(options.selectedIncludes.toList())
                siteObj.put("include", includesArray)
            }

            if (options.usersInput.isNotBlank()) {
                val usersList = options.usersInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                if (usersList.isNotEmpty()) {
                    siteObj.put("users", JSONArray(usersList))
                }
            }

            if (options.customArgs.isNotBlank()) {
                siteObj.put("custom-flags", options.customArgs)
            }

            // Inject site-specific custom extractor settings
            val siteCustoms = customExtractorOptions[siteId] ?: emptyMap()
            for ((k, v) in siteCustoms) {
                if (v.isNotBlank()) {
                    if (v.startsWith("[") && v.endsWith("]")) {
                        try {
                            siteObj.put(k, JSONArray(v))
                        } catch (e: Exception) {
                            siteObj.put(k, v)
                        }
                    } else if (v.equals("true", ignoreCase = true) || v.equals("false", ignoreCase = true)) {
                        siteObj.put(k, v.toBoolean())
                    } else if (v.toIntOrNull() != null) {
                        siteObj.put(k, v.toInt())
                    } else {
                        siteObj.put(k, v)
                    }
                }
            }

            extractor.put(siteId, siteObj)
        }

        root.put("extractor", extractor)
        val configContent = root.toString(2)

        try {
            configJsonFile.writeText(configContent)
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error writing config.json", e)
        }

        return configContent
    }

    fun writeRawConfigFile(content: String) {
        try {
            configJsonFile.writeText(content)
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error writing raw config.json", e)
        }
    }

    fun readConfigFileContent(): String {
        return try {
            if (configJsonFile.exists()) {
                configJsonFile.readText()
            } else {
                "{}"
            }
        } catch (e: Exception) {
            "{}"
        }
    }

    fun getConfigFilePath(): String = configJsonFile.absolutePath

    fun getGalleryDlRepoDir(): File = File(context.filesDir, "gallery_dl")

    fun unpackGalleryDlRepository(): File {
        val targetDir = getGalleryDlRepoDir()
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }
        try {
            unpackAssetFolder("gallery-dl-repo", targetDir)
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error unpacking gallery-dl repository assets", e)
        }
        return targetDir
    }

    private fun unpackAssetFolder(assetPath: String, targetDir: File) {
        val assets = context.assets.list(assetPath) ?: return
        if (assets.isEmpty()) {
            try {
                context.assets.open(assetPath).use { input ->
                    val fileName = assetPath.substringAfterLast("/")
                    File(targetDir, fileName).outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                Log.e("ConfigRepository", "Failed to copy asset $assetPath", e)
            }
        } else {
            val subDirName = assetPath.substringAfterLast("/")
            val currentTargetDir = if (subDirName == "gallery-dl-repo") targetDir else File(targetDir, subDirName)
            if (!currentTargetDir.exists()) {
                currentTargetDir.mkdirs()
            }
            for (file in assets) {
                val subAssetPath = "$assetPath/$file"
                unpackAssetFolder(subAssetPath, currentTargetDir)
            }
        }
    }
}
