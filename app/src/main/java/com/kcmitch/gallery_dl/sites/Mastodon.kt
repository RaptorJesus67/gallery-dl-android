package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Mastodon (joinmastodon.org)
 */
class Mastodon {
    val siteId: String = "mastodon"
    val siteName: String = "Mastodon"
    val domain: String = "joinmastodon.org"
    val category: String = "Social Media"
    val emojiIcon: String = "🐘"
    val brandColorHex: Long = 0xFF6364FF
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("statuses", "media", "pins")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "mastodon" to mapOf(
                "include" to listOf("statuses", "media"),
                "replies" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("mastodon") || url.contains("mstdn")
    }
}
