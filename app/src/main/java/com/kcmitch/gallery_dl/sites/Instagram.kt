package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Instagram (instagram.com)
 */
class Instagram {
    val siteId: String = "instagram"
    val siteName: String = "Instagram"
    val domain: String = "instagram.com"
    val category: String = "Social Media"
    val emojiIcon: String = "📸"
    val brandColorHex: Long = 0xFF5B67CA
    val requiresCookies: Boolean = true

    val availableIncludes: List<String> = listOf("avatar", "posts", "stories", "reels", "highlights", "tagged")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "instagram" to mapOf(
                "include" to listOf("posts", "reels", "stories"),
                "videos" to true,
                "stories" to false,
                "reels" to true,
                "highlights" to false,
                "tagged" to false,
                "avatar" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("instagram.com") || url.contains("instagr.am")
    }
}
