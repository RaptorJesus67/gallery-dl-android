package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for ArtStation (artstation.com)
 */
class ArtStation {
    val siteId: String = "artstation"
    val siteName: String = "ArtStation"
    val domain: String = "artstation.com"
    val category: String = "Art & Illustration"
    val emojiIcon: String = "🖌️"
    val brandColorHex: Long = 0xFF13AFF0
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("projects", "artworks", "likes")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "artstation" to mapOf(
                "include" to listOf("projects", "artworks"),
                "assets" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("artstation.com")
    }
}
