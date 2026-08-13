package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Bilibili (bilibili.com)
 */
class Bilibili {
    val siteId: String = "bilibili"
    val siteName: String = "Bilibili"
    val domain: String = "bilibili.com"
    val category: String = "Video & Audio"
    val emojiIcon: String = "📺"
    val brandColorHex: Long = 0xFF00A1D6
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("videos", "dynamics")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "bilibili" to mapOf(
                "include" to listOf("videos"),
                "danmaku" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("bilibili.com") || url.contains("b23.tv")
    }
}
