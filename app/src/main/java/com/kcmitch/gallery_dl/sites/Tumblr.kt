package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Tumblr (tumblr.com)
 */
class Tumblr {
    val siteId: String = "tumblr"
    val siteName: String = "Tumblr"
    val domain: String = "tumblr.com"
    val category: String = "Blogging"
    val emojiIcon: String = "💬"
    val brandColorHex: Long = 0xFF36465D
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("posts", "likes", "reblogs", "tagged")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "tumblr" to mapOf(
                "include" to listOf("posts"),
                "reblogs" to false,
                "inline" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("tumblr.com")
    }
}
