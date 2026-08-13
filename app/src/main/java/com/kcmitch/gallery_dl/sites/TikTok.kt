package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for TikTok (tiktok.com)
 */
class TikTok {
    val siteId: String = "tiktok"
    val siteName: String = "TikTok"
    val domain: String = "tiktok.com"
    val category: String = "Video & Audio"
    val emojiIcon: String = "🎵"
    val brandColorHex: Long = 0xFF00F2FE
    val requiresCookies: Boolean = true

    val availableIncludes: List<String> = listOf("videos", "likes", "avatars")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "tiktok" to mapOf(
                "include" to listOf("videos"),
                "watermark" to false,
                "likes" to false,
                "avatar" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("tiktok.com")
    }
}
