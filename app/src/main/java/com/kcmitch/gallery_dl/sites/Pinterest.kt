package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Pinterest (pinterest.com)
 */
class Pinterest {
    val siteId: String = "pinterest"
    val siteName: String = "Pinterest"
    val domain: String = "pinterest.com"
    val category: String = "Image Board"
    val emojiIcon: String = "📌"
    val brandColorHex: Long = 0xFFE60023
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("pins", "boards", "sections")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "pinterest" to mapOf(
                "include" to listOf("pins", "boards"),
                "sections" to true,
                "videos" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("pinterest.com") || url.contains("pin.it")
    }
}
