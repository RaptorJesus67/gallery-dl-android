package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for DeviantArt (deviantart.com)
 */
class DeviantArt {
    val siteId: String = "deviantart"
    val siteName: String = "DeviantArt"
    val domain: String = "deviantart.com"
    val category: String = "Art & Illustration"
    val emojiIcon: String = "🖼️"
    val brandColorHex: Long = 0xFF05CC47
    val requiresCookies: Boolean = true

    val availableIncludes: List<String> = listOf("gallery", "scraps", "favorites", "journals")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "deviantart" to mapOf(
                "include" to listOf("gallery"),
                "scraps" to false,
                "journals" to false,
                "sta.sh" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("deviantart.com") || url.contains("fav.me")
    }
}
