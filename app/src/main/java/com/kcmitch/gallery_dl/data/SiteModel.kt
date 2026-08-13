package com.kcmitch.gallery_dl.data

data class SupportedSite(
    val id: String,
    val name: String,
    val category: String,
    val domain: String,
    val description: String,
    val availableIncludes: List<String>,
    val requiresCookies: Boolean = false,
    val brandColorHex: Long = 0xFF4338CA,
    val emojiIcon: String = "📸"
)

data class SiteOptions(
    val siteId: String,
    val cookieText: String = "",
    val cookieFilePath: String = "",
    val usersInput: String = "",
    val selectedIncludes: Set<String> = emptySet(),
    val includeOrder: List<String> = emptyList(),
    val customArgs: String = "",
    val taggedFilterMode: String = "all", // "all" or "users"
    val taggedUsersInput: String = "", // e.g. "creator1, creator2"
    val verboseFlag: Boolean = false,
    val simulateFlag: Boolean = false
)

data class AppSettings(
    val autoSave: Boolean = true,
    val themeMode: String = "light", // "light", "dark", "system"
    val themePreset: String = "cobalt", // "cobalt", "coral", "lavender", "facebook", "instagram", etc.
    val uiStyle: String = "minimalist", // "minimalist", "creator", "express", "terminal"
    val downloadDirectory: String = "/storage/emulated/0/Download/gallery-dl",
    val rateLimit: String = "100K",
    val retries: Int = 3
)

data class DownloadLogEntry(
    val timestamp: String,
    val message: String,
    val type: LogType = LogType.INFO
)

enum class LogType {
    INFO, SUCCESS, WARNING, ERROR, PROGRESS
}
