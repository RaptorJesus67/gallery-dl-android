package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Gelbooru (gelbooru.com)
 */
class Gelbooru {
    val siteId: String = "gelbooru"
    val siteName: String = "Gelbooru"
    val domain: String = "gelbooru.com"
    val category: String = "Booru & Anime"
    val emojiIcon: String = "⛩️"
    val brandColorHex: Long = 0xFF006699
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("posts", "pools")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "gelbooru" to mapOf(
                "include" to listOf("posts"),
                "pools" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("gelbooru.com")
    }
}
