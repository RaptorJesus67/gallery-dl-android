package com.kcmitch.gallery_dl.sites

/**
 * Site definition and configuration builder for Weibo (weibo.com)
 */
class Weibo {
    val siteId: String = "weibo"
    val siteName: String = "Weibo"
    val domain: String = "weibo.com"
    val category: String = "Social Media"
    val emojiIcon: String = "🌐"
    val brandColorHex: Long = 0xFFE6162D
    val requiresCookies: Boolean = true

    val availableIncludes: List<String> = listOf("posts", "images", "videos")

    fun getDefaultExtractorConfig(): Map<String, Any> {
        return mapOf(
            "weibo" to mapOf(
                "include" to listOf("posts", "images"),
                "videos" to true
            )
        )
    }

    fun isValidUrl(url: String): Boolean {
        return url.contains("weibo.com") || url.contains("weibo.cn")
    }
}
