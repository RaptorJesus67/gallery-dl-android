package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Flickr (flickr.com)
 */
class Flickr {
    val siteId: String = "flickr"
    val siteName: String = "Flickr"
    val domain: String = "flickr.com"
    val category: String = "Photography"
    val emojiIcon: String = "📸"
    val brandColorHex: Long = 0xFFFF0084
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("photostream", "albums", "favorites")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "flickr" to mapOf(
                "include" to listOf("photostream", "albums"),
                "size" to "orig"
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("flickr.com") || url.contains("flic.kr")
    }
}
