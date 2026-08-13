package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Reddit (reddit.com)
 */
class Reddit {
    val siteId: String = "reddit"
    val siteName: String = "Reddit"
    val domain: String = "reddit.com"
    val category: String = "Social Media"
    val emojiIcon: String = "🤖"
    val brandColorHex: Long = 0xFFFF4500
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("posts", "comments", "saved", "upvoted", "galleries")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "reddit" to mapOf(
                "include" to listOf("posts", "galleries"),
                "comments" to false,
                "saved" to false,
                "upvoted" to false,
                "videos" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("reddit.com") || url.contains("redd.it")
    }
}
