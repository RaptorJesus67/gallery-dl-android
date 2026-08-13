package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Pixiv (pixiv.net)
 */
class Pixiv {
    val siteId: String = "pixiv"
    val siteName: String = "Pixiv"
    val domain: String = "pixiv.net"
    val category: String = "Art & Illustration"
    val emojiIcon: String = "🎨"
    val brandColorHex: Long = 0xFF0096FA
    val requiresCookies: Boolean = true

    val availableIncludes: List<String> = listOf("illustrations", "manga", "bookmarks", "rankings")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "pixiv" to mapOf(
                "include" to listOf("illustrations", "manga"),
                "ugoira" to true,
                "ugoira-conv" to true,
                "manga" to true,
                "bookmarks" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("pixiv.net") || url.contains("pixiv.me")
    }
}
