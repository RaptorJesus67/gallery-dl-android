package com.kcmitch.gallery_dl.data

object SupportedSitesData {
    val allSites: List<SupportedSite> = listOf(
        SupportedSite(
            id = "instagram",
            name = "Instagram",
            category = "Social Media",
            domain = "instagram.com",
            description = "Posts, stories, reels, highlights, tagged media, avatar",
            availableIncludes = listOf("avatar", "posts", "stories", "reels", "highlights", "tagged"),
            requiresCookies = true,
            brandColorHex = 0xFF5B67CA,
            emojiIcon = "📸"
        ),
        SupportedSite(
            id = "tiktok",
            name = "TikTok",
            category = "Video & Audio",
            domain = "tiktok.com",
            description = "User videos, liked videos, music posts",
            availableIncludes = listOf("videos", "likes", "avatars"),
            requiresCookies = true,
            brandColorHex = 0xFF00F2FE,
            emojiIcon = "🎵"
        ),
        SupportedSite(
            id = "twitter",
            name = "Twitter / X",
            category = "Social Media",
            domain = "twitter.com / x.com",
            description = "Tweets, replies, retweets, media, likes, bookmarks",
            availableIncludes = listOf("tweets", "replies", "retweets", "media", "likes", "bookmarks"),
            requiresCookies = true,
            brandColorHex = 0xFF1DA1F2,
            emojiIcon = "🐦"
        ),
        SupportedSite(
            id = "reddit",
            name = "Reddit",
            category = "Social Media",
            domain = "reddit.com",
            description = "Subreddit posts, user submissions, saved posts, upvoted",
            availableIncludes = listOf("posts", "comments", "saved", "upvoted", "galleries"),
            requiresCookies = false,
            brandColorHex = 0xFFFF4500,
            emojiIcon = "🤖"
        ),
        SupportedSite(
            id = "pinterest",
            name = "Pinterest",
            category = "Image Board",
            domain = "pinterest.com",
            description = "Pins, user boards, sections, created pins",
            availableIncludes = listOf("pins", "boards", "sections"),
            requiresCookies = false,
            brandColorHex = 0xFFE60023,
            emojiIcon = "📌"
        ),
        SupportedSite(
            id = "youtube",
            name = "YouTube",
            category = "Video & Audio",
            domain = "youtube.com",
            description = "Videos, shorts, playlists, community posts",
            availableIncludes = listOf("videos", "shorts", "playlists", "community"),
            requiresCookies = false,
            brandColorHex = 0xFFFF0000,
            emojiIcon = "▶️"
        ),
        SupportedSite(
            id = "pixiv",
            name = "Pixiv",
            category = "Art & Illustration",
            domain = "pixiv.net",
            description = "Illustrations, manga, bookmarks, rankings",
            availableIncludes = listOf("illustrations", "manga", "bookmarks", "rankings"),
            requiresCookies = true,
            brandColorHex = 0xFF0096FA,
            emojiIcon = "🎨"
        ),
        SupportedSite(
            id = "deviantart",
            name = "DeviantArt",
            category = "Art & Illustration",
            domain = "deviantart.com",
            description = "Galleries, scraps, favorites, journals",
            availableIncludes = listOf("gallery", "scraps", "favorites", "journals"),
            requiresCookies = true,
            brandColorHex = 0xFF05CC47,
            emojiIcon = "🖼️"
        ),
        SupportedSite(
            id = "tumblr",
            name = "Tumblr",
            category = "Blogging",
            domain = "tumblr.com",
            description = "Posts, likes, reblogs, tagged posts",
            availableIncludes = listOf("posts", "likes", "reblogs", "tagged"),
            requiresCookies = false,
            brandColorHex = 0xFF36465D,
            emojiIcon = "💬"
        ),
        SupportedSite(
            id = "danbooru",
            name = "Danbooru",
            category = "Booru & Anime",
            domain = "danbooru.donmai.us",
            description = "Posts by tag, pools, favorites",
            availableIncludes = listOf("posts", "pools", "favorites"),
            requiresCookies = false,
            brandColorHex = 0xFF2B5B84,
            emojiIcon = "🌸"
        ),
        SupportedSite(
            id = "imgur",
            name = "Imgur",
            category = "Image Hosting",
            domain = "imgur.com",
            description = "Albums, user galleries, images",
            availableIncludes = listOf("albums", "galleries", "images"),
            requiresCookies = false,
            brandColorHex = 0xFF1BB76E,
            emojiIcon = "📷"
        ),
        SupportedSite(
            id = "artstation",
            name = "ArtStation",
            category = "Art & Illustration",
            domain = "artstation.com",
            description = "Projects, artwork, likes, collections",
            availableIncludes = listOf("projects", "artworks", "likes"),
            requiresCookies = false,
            brandColorHex = 0xFF13AFF0,
            emojiIcon = "🖌️"
        ),
        SupportedSite(
            id = "bsky",
            name = "Bluesky",
            category = "Social Media",
            domain = "bsky.app",
            description = "Posts, reposts, media, feeds",
            availableIncludes = listOf("posts", "reposts", "media"),
            requiresCookies = false,
            brandColorHex = 0xFF1185FE,
            emojiIcon = "🦋"
        ),
        SupportedSite(
            id = "mastodon",
            name = "Mastodon",
            category = "Social Media",
            domain = "joinmastodon.org",
            description = "Statuses, media attachments, pins",
            availableIncludes = listOf("statuses", "media", "pins"),
            requiresCookies = false,
            brandColorHex = 0xFF6364FF,
            emojiIcon = "🐘"
        ),
        SupportedSite(
            id = "flickr",
            name = "Flickr",
            category = "Photography",
            domain = "flickr.com",
            description = "Photostreams, albums, favorites, galleries",
            availableIncludes = listOf("photostream", "albums", "favorites"),
            requiresCookies = false,
            brandColorHex = 0xFFFF0084,
            emojiIcon = "📸"
        ),
        SupportedSite(
            id = "gelbooru",
            name = "Gelbooru",
            category = "Booru & Anime",
            domain = "gelbooru.com",
            description = "Booru posts, tag searches, pools",
            availableIncludes = listOf("posts", "pools"),
            requiresCookies = false,
            brandColorHex = 0xFF006699,
            emojiIcon = "⛩️"
        ),
        SupportedSite(
            id = "kemono",
            name = "Kemono",
            category = "Archival & Creators",
            domain = "kemono.su",
            description = "Creator posts, attachments, illustrations",
            availableIncludes = listOf("posts", "attachments"),
            requiresCookies = false,
            brandColorHex = 0xFFFF9900,
            emojiIcon = "🐾"
        ),
        SupportedSite(
            id = "soundcloud",
            name = "SoundCloud",
            category = "Video & Audio",
            domain = "soundcloud.com",
            description = "Tracks, sets, likes, reposts",
            availableIncludes = listOf("tracks", "sets", "likes"),
            requiresCookies = false,
            brandColorHex = 0xFFFF5500,
            emojiIcon = "🎧"
        ),
        SupportedSite(
            id = "bilibili",
            name = "Bilibili",
            category = "Video & Audio",
            domain = "bilibili.com",
            description = "User videos, dynamic posts, space",
            availableIncludes = listOf("videos", "dynamics"),
            requiresCookies = false,
            brandColorHex = 0xFF00A1D6,
            emojiIcon = "📺"
        ),
        SupportedSite(
            id = "weibo",
            name = "Weibo",
            category = "Social Media",
            domain = "weibo.com",
            description = "User posts, status images, videos",
            availableIncludes = listOf("posts", "images", "videos"),
            requiresCookies = true,
            brandColorHex = 0xFFE6162D,
            emojiIcon = "🌐"
        )
    )

    val defaultFavorites = listOf("instagram", "tiktok", "twitter", "reddit", "pinterest", "youtube", "pixiv")

    fun getSiteById(id: String): SupportedSite? {
        return allSites.find { it.id.equals(id, ignoreCase = true) }
    }
}
