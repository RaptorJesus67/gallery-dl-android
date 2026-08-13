package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for SoundCloud (soundcloud.com)
 */
class SoundCloud {
    val siteId: String = "soundcloud"
    val siteName: String = "SoundCloud"
    val domain: String = "soundcloud.com"
    val category: String = "Video & Audio"
    val emojiIcon: String = "🎧"
    val brandColorHex: Long = 0xFFFF5500
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("tracks", "sets", "likes")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "soundcloud" to mapOf(
                "include" to listOf("tracks", "sets"),
                "artwork" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("soundcloud.com")
    }
}
