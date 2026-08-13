package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Danbooru (danbooru.donmai.us)
 */
class Danbooru {
    val siteId: String = "danbooru"
    val siteName: String = "Danbooru"
    val domain: String = "danbooru.donmai.us"
    val category: String = "Booru & Anime"
    val emojiIcon: String = "🌸"
    val brandColorHex: Long = 0xFF2B5B84
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("posts", "pools", "favorites")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "danbooru" to mapOf(
                "include" to listOf("posts"),
                "pools" to true,
                "popular" to false
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("danbooru")
    }
}
