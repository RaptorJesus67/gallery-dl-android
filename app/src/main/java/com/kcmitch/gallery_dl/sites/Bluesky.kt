package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Bluesky (bsky.app)
 */
class Bluesky {
    val siteId: String = "bsky"
    val siteName: String = "Bluesky"
    val domain: String = "bsky.app"
    val category: String = "Social Media"
    val emojiIcon: String = "🦋"
    val brandColorHex: Long = 0xFF1185FE
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("posts", "reposts", "media")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "bsky" to mapOf(
                "include" to listOf("posts", "media"),
                "reposts" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("bsky.app") || url.contains("bsky.social")
    }
}
