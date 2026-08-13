package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Twitter / X (twitter.com / x.com)
 */
class Twitter {
    val siteId: String = "twitter"
    val siteName: String = "Twitter / X"
    val domain: String = "twitter.com / x.com"
    val category: String = "Social Media"
    val emojiIcon: String = "🐦"
    val brandColorHex: Long = 0xFF1DA1F2
    val requiresCookies: Boolean = true

    val availableIncludes: List<String> = listOf("tweets", "replies", "retweets", "media", "likes", "bookmarks")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "twitter" to mapOf(
                "include" to listOf("tweets", "media"),
                "replies" to false,
                "retweets" to false,
                "videos" to true,
                "text-tweets" to false,
                "cards" to true,
                "quality" to "orig"
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("twitter.com") || url.contains("x.com")
    }
}
