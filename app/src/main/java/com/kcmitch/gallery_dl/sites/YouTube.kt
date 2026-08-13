package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for YouTube (youtube.com)
 */
class YouTube {
    val siteId: String = "youtube"
    val siteName: String = "YouTube"
    val domain: String = "youtube.com"
    val category: String = "Video & Audio"
    val emojiIcon: String = "▶️"
    val brandColorHex: Long = 0xFFFF0000
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("videos", "shorts", "playlists", "community")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "youtube" to mapOf(
                "include" to listOf("videos", "shorts", "community"),
                "shorts" to true,
                "community" to true,
                "playlists" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("youtube.com") || url.contains("youtu.be")
    }
}
