package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Imgur (imgur.com)
 */
class Imgur {
    val siteId: String = "imgur"
    val siteName: String = "Imgur"
    val domain: String = "imgur.com"
    val category: String = "Image Hosting"
    val emojiIcon: String = "📷"
    val brandColorHex: Long = 0xFF1BB76E
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("albums", "galleries", "images")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "imgur" to mapOf(
                "include" to listOf("albums", "images"),
                "mp4" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("imgur.com")
    }
}
