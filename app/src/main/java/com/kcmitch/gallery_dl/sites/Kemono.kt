package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Kemono (kemono.su)
 */
class Kemono {
    val siteId: String = "kemono"
    val siteName: String = "Kemono"
    val domain: String = "kemono.su"
    val category: String = "Archival & Creators"
    val emojiIcon: String = "🐾"
    val brandColorHex: Long = 0xFFFF9900
    val requiresCookies: Boolean = false

    val availableIncludes: List<String> = listOf("posts", "attachments")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "kemono" to mapOf(
                "include" to listOf("posts", "attachments"),
                "inline" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("kemono.su") || url.contains("kemono.party")
    }
}
