package com.kcmitch.gallery_dl.data

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

enum class PaletteGroup {
    CURRENT,
    SITE,
    CUSTOM
}

data class PaletteInfo(
    val id: String,
    val name: String,
    val desc: String,
    val group: PaletteGroup,
    val swatches: List<Color>,
    val colorScheme: ColorScheme,
    val gradientTop: Color,
    val gradientBottom: Color,
    val isDarkBg: Boolean,
    val closedContainerBg: Color,
    val closedContainerTextColor: Color
)

object PaletteManager {
    // -------------------------------------------------------------
    // GROUP 3: CUSTOM PALETTES (Alphabetically A -> Z)
    // -------------------------------------------------------------
    val cobaltPalette = PaletteInfo(
        id = "cobalt",
        name = "Cobalt Blue",
        desc = "Vibrant Navy & Electric Sky Accent",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFF38BDF8), Color(0xFF2563EB), Color(0xFF0B193C)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF38BDF8),
            onPrimary = Color(0xFF00325A),
            primaryContainer = Color(0xFF1E3A8A),
            onPrimaryContainer = Color(0xFFE0F2FE),
            secondary = Color(0xFF60A5FA),
            secondaryContainer = Color(0xFF1D4ED8),
            onSecondaryContainer = Color(0xFFEFF6FF),
            background = Color(0xFF09132B),
            onBackground = Color(0xFFF0F6FF),
            surface = Color(0xFF0E1A38),
            onSurface = Color(0xFFF0F6FF),
            surfaceVariant = Color(0xFF18284E),
            onSurfaceVariant = Color(0xFF93C5FD)
        ),
        gradientTop = Color(0xFF0E1F42),
        gradientBottom = Color(0xFF060D1E),
        isDarkBg = true,
        closedContainerBg = Color(0xFF2563EB),
        closedContainerTextColor = Color.White
    )

    val coralPalette = PaletteInfo(
        id = "coral",
        name = "Coral Sunset",
        desc = "Coral Pink, Tropical Peach & Warm Light",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFFFF6F61), Color(0xFFFFA07A), Color(0xFFFFF0EE)),
        colorScheme = lightColorScheme(
            primary = Color(0xFFFF6F61),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFDAD6),
            onPrimaryContainer = Color(0xFF410002),
            secondary = Color(0xFFE05547),
            secondaryContainer = Color(0xFFFFDBCF),
            onSecondaryContainer = Color(0xFF3B0B00),
            background = Color(0xFFFFF0EE),
            onBackground = Color(0xFF3D1412),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF3D1412),
            surfaceVariant = Color(0xFFFCDED9),
            onSurfaceVariant = Color(0xFF8C2219)
        ),
        gradientTop = Color(0xFFFFFFFF),
        gradientBottom = Color(0xFFFFF0EE),
        isDarkBg = false,
        closedContainerBg = Color(0xFFFF6F61),
        closedContainerTextColor = Color.White
    )

    val lavenderPalette = PaletteInfo(
        id = "lavender",
        name = "Lavender Violet",
        desc = "Light Lavender, Violet & Neon Orchid",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFF9333EA), Color(0xFFC084FC), Color(0xFFF5F3FF)),
        colorScheme = lightColorScheme(
            primary = Color(0xFF9333EA),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFF3E8FF),
            onPrimaryContainer = Color(0xFF3B0764),
            secondary = Color(0xFFA855F7),
            secondaryContainer = Color(0xFFFAE8FF),
            onSecondaryContainer = Color(0xFF581C87),
            background = Color(0xFFF5F3FF),
            onBackground = Color(0xFF1E132B),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF1E132B),
            surfaceVariant = Color(0xFFE9D5FF),
            onSurfaceVariant = Color(0xFF6B21A8)
        ),
        gradientTop = Color(0xFFFFFFFF),
        gradientBottom = Color(0xFFF5F3FF),
        isDarkBg = false,
        closedContainerBg = Color(0xFF9333EA),
        closedContainerTextColor = Color.White
    )

    val morningPalette = PaletteInfo(
        id = "morning",
        name = "Morning Gold",
        desc = "Sunrise Gold, Amber & Radiant Light",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFFF59E0B), Color(0xFFD97706), Color(0xFFFFFBEB)),
        colorScheme = lightColorScheme(
            primary = Color(0xFFD97706),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFEF3C7),
            onPrimaryContainer = Color(0xFF78350F),
            secondary = Color(0xFFF59E0B),
            secondaryContainer = Color(0xFFFFEAD0),
            onSecondaryContainer = Color(0xFF451A03),
            background = Color(0xFFFFFBEB),
            onBackground = Color(0xFF451A03),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF451A03),
            surfaceVariant = Color(0xFFFEF3C7),
            onSurfaceVariant = Color(0xFF92400E)
        ),
        gradientTop = Color(0xFFFFFBF0),
        gradientBottom = Color(0xFFFEF3C7),
        isDarkBg = false,
        closedContainerBg = Color(0xFFB45309),
        closedContainerTextColor = Color.White
    )

    val oceanPalette = PaletteInfo(
        id = "ocean",
        name = "Ocean Abyssal",
        desc = "Oceanic Aqua & Dark Abyssal Blue",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFF06B6D4), Color(0xFF0284C7), Color(0xFF031E30)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF38BDF8),
            onPrimary = Color(0xFF003656),
            primaryContainer = Color(0xFF004D7A),
            onPrimaryContainer = Color(0xFFE0F2FE),
            secondary = Color(0xFF22D3EE),
            secondaryContainer = Color(0xFF08627A),
            onSecondaryContainer = Color(0xFFCFFAFE),
            background = Color(0xFF031422),
            onBackground = Color(0xFFECFEFF),
            surface = Color(0xFF061E30),
            onSurface = Color(0xFFECFEFF),
            surfaceVariant = Color(0xFF0C2E46),
            onSurfaceVariant = Color(0xFFA5F3FC)
        ),
        gradientTop = Color(0xFF08263D),
        gradientBottom = Color(0xFF020E18),
        isDarkBg = true,
        closedContainerBg = Color(0xFF0284C7),
        closedContainerTextColor = Color.White
    )

    val rosesPalette = PaletteInfo(
        id = "roses",
        name = "Roses Crimson",
        desc = "Crimson Rose, Velvet Red & Dark Charcoal",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFFF43F5E), Color(0xFFBE123C), Color(0xFF1F090D)),
        colorScheme = darkColorScheme(
            primary = Color(0xFFFB7185),
            onPrimary = Color(0xFF4C0519),
            primaryContainer = Color(0xFF881337),
            onPrimaryContainer = Color(0xFFFFE4E6),
            secondary = Color(0xFFF43F5E),
            secondaryContainer = Color(0xFF9F1239),
            onSecondaryContainer = Color(0xFFFFE4E6),
            background = Color(0xFF170609),
            onBackground = Color(0xFFFFF1F2),
            surface = Color(0xFF220A0E),
            onSurface = Color(0xFFFFF1F2),
            surfaceVariant = Color(0xFF351218),
            onSurfaceVariant = Color(0xFFFECDD3)
        ),
        gradientTop = Color(0xFF2B0C12),
        gradientBottom = Color(0xFF100305),
        isDarkBg = true,
        closedContainerBg = Color(0xFFE11D48),
        closedContainerTextColor = Color.White
    )

    val saharaPalette = PaletteInfo(
        id = "sahara",
        name = "Sahara Dune",
        desc = "Desert Khaki, Warm Sand & Terra Cotta",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFFA67C1E), Color(0xFF8C621E), Color(0xFFF5EBD8)),
        colorScheme = lightColorScheme(
            primary = Color(0xFFA67C1E),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE8D3A7),
            onPrimaryContainer = Color(0xFF382600),
            secondary = Color(0xFF8C621E),
            secondaryContainer = Color(0xFFE2CFA8),
            onSecondaryContainer = Color(0xFF2E1C00),
            background = Color(0xFFF5EBD8),
            onBackground = Color(0xFF362817),
            surface = Color(0xFFFAF4E8),
            onSurface = Color(0xFF362817),
            surfaceVariant = Color(0xFFE8DBC3),
            onSurfaceVariant = Color(0xFF5E461A)
        ),
        gradientTop = Color(0xFFFAF4E8),
        gradientBottom = Color(0xFFF5EBD8),
        isDarkBg = false,
        closedContainerBg = Color(0xFFA67C1E),
        closedContainerTextColor = Color.White
    )

    val woodlandsPalette = PaletteInfo(
        id = "woodlands",
        name = "Woodlands Forest",
        desc = "Emerald Forest, Pine & Cedar Earth",
        group = PaletteGroup.CUSTOM,
        swatches = listOf(Color(0xFF22C55E), Color(0xFF15803D), Color(0xFF0A2010)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF4ADE80),
            onPrimary = Color(0xFF052E16),
            primaryContainer = Color(0xFF14532D),
            onPrimaryContainer = Color(0xFFDCFCE7),
            secondary = Color(0xFF22C55E),
            secondaryContainer = Color(0xFF166534),
            onSecondaryContainer = Color(0xFFDCFCE7),
            background = Color(0xFF08180C),
            onBackground = Color(0xFFF0FDF4),
            surface = Color(0xFF0E2213),
            onSurface = Color(0xFFF0FDF4),
            surfaceVariant = Color(0xFF18331E),
            onSurfaceVariant = Color(0xFF86EFAC)
        ),
        gradientTop = Color(0xFF132B18),
        gradientBottom = Color(0xFF051007),
        isDarkBg = true,
        closedContainerBg = Color(0xFF16A34A),
        closedContainerTextColor = Color.White
    )

    // -------------------------------------------------------------
    // GROUP 2: SITE SPECIFIC PALETTES (Alphabetically A -> Z)
    // -------------------------------------------------------------
    val blueskyPalette = PaletteInfo(
        id = "bluesky",
        name = "Bluesky",
        desc = "Sky Blue & Cloud Atmosphere",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFF0085FF), Color(0xFF0055B3), Color(0xFF0B1928)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF0085FF),
            onPrimary = Color.White,
            primaryContainer = Color(0xFF004080),
            onPrimaryContainer = Color(0xFFD6EBFF),
            secondary = Color(0xFF339DFF),
            secondaryContainer = Color(0xFF003060),
            onSecondaryContainer = Color(0xFFE6F2FF),
            background = Color(0xFF06121E),
            onBackground = Color(0xFFF0F8FF),
            surface = Color(0xFF0B1A2B),
            onSurface = Color(0xFFF0F8FF),
            surfaceVariant = Color(0xFF12273F),
            onSurfaceVariant = Color(0xFF99CBFF)
        ),
        gradientTop = Color(0xFF0E2238),
        gradientBottom = Color(0xFF040A12),
        isDarkBg = true,
        closedContainerBg = Color(0xFF0085FF),
        closedContainerTextColor = Color.White
    )

    val deviantArtPalette = PaletteInfo(
        id = "deviantart",
        name = "DeviantArt",
        desc = "DA Vibrant Green & Charcoal Slate",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFF00E59B), Color(0xFF00B377), Color(0xFF0B1116)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF00E59B),
            onPrimary = Color(0xFF003822),
            primaryContainer = Color(0xFF005837),
            onPrimaryContainer = Color(0xFF8BFFCE),
            secondary = Color(0xFF00B377),
            secondaryContainer = Color(0xFF19232D),
            onSecondaryContainer = Color(0xFF70FFC2),
            background = Color(0xFF0B1116),
            onBackground = Color(0xFFEAF5ED),
            surface = Color(0xFF131D24),
            onSurface = Color(0xFFEAF5ED),
            surfaceVariant = Color(0xFF1D2B35),
            onSurfaceVariant = Color(0xFF00E59B)
        ),
        gradientTop = Color(0xFF16252F),
        gradientBottom = Color(0xFF070B0E),
        isDarkBg = true,
        closedContainerBg = Color(0xFF00E59B),
        closedContainerTextColor = Color(0xFF0B1116)
    )

    val facebookPalette = PaletteInfo(
        id = "facebook",
        name = "Facebook",
        desc = "Meta Blue & Light Whitish Gray Canvas",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFF1877F2), Color(0xFF166FE5), Color(0xFFF0F2F5)),
        colorScheme = lightColorScheme(
            primary = Color(0xFF1877F2),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE7F3FF),
            onPrimaryContainer = Color(0xFF052C65),
            secondary = Color(0xFF166FE5),
            secondaryContainer = Color(0xFFD8E4F8),
            onSecondaryContainer = Color(0xFF002244),
            background = Color(0xFFF0F2F5),
            onBackground = Color(0xFF050505),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF050505),
            surfaceVariant = Color(0xFFE4E6EB),
            onSurfaceVariant = Color(0xFF65676B)
        ),
        gradientTop = Color(0xFFFFFFFF),
        gradientBottom = Color(0xFFF0F2F5),
        isDarkBg = false,
        closedContainerBg = Color(0xFF1877F2),
        closedContainerTextColor = Color.White
    )

    val flickrPalette = PaletteInfo(
        id = "flickr",
        name = "Flickr",
        desc = "Flickr Pink, Blue & Light Gray Canvas",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFF3898EC), Color(0xFFEA384C), Color(0xFFF3F5F6)),
        colorScheme = lightColorScheme(
            primary = Color(0xFFEA384C),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFDCDD),
            onPrimaryContainer = Color(0xFF5C000C),
            secondary = Color(0xFF3898EC),
            secondaryContainer = Color(0xFFD3E8FF),
            onSecondaryContainer = Color(0xFF003060),
            background = Color(0xFFF3F5F6),
            onBackground = Color(0xFF212124),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF212124),
            surfaceVariant = Color(0xFFE5E8EA),
            onSurfaceVariant = Color(0xFF63656A)
        ),
        gradientTop = Color(0xFFFFFFFF),
        gradientBottom = Color(0xFFF3F5F6),
        isDarkBg = false,
        closedContainerBg = Color(0xFFEA384C),
        closedContainerTextColor = Color.White
    )

    val imgurPalette = PaletteInfo(
        id = "imgur",
        name = "Imgur",
        desc = "Imgur Emerald & Dark Charcoal",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFF1BB76E), Color(0xFF141518), Color(0xFF0B0C0E)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF1BB76E),
            onPrimary = Color(0xFF00381E),
            primaryContainer = Color(0xFF0D5E38),
            onPrimaryContainer = Color(0xFFB3F5D3),
            secondary = Color(0xFF22D37F),
            secondaryContainer = Color(0xFF141518),
            onSecondaryContainer = Color(0xFFC3FBE0),
            background = Color(0xFF0B0C0E),
            onBackground = Color(0xFFEEF2EF),
            surface = Color(0xFF14161A),
            onSurface = Color(0xFFEEF2EF),
            surfaceVariant = Color(0xFF1F2228),
            onSurfaceVariant = Color(0xFF1BB76E)
        ),
        gradientTop = Color(0xFF1A1C21),
        gradientBottom = Color(0xFF070809),
        isDarkBg = true,
        closedContainerBg = Color(0xFF1BB76E),
        closedContainerTextColor = Color(0xFF0B0C0E)
    )

    val instagramPalette = PaletteInfo(
        id = "instagram",
        name = "Instagram",
        desc = "Insta Magenta, Crimson & Coral Sunset",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFFE1306C), Color(0xFF833AB4), Color(0xFF120A14)),
        colorScheme = darkColorScheme(
            primary = Color(0xFFE1306C),
            onPrimary = Color.White,
            primaryContainer = Color(0xFF7A1236),
            onPrimaryContainer = Color(0xFFFFD8E4),
            secondary = Color(0xFFC13584),
            secondaryContainer = Color(0xFF531952),
            onSecondaryContainer = Color(0xFFFBD7FA),
            background = Color(0xFF120A14),
            onBackground = Color(0xFFFCF5FF),
            surface = Color(0xFF1C1020),
            onSurface = Color(0xFFFCF5FF),
            surfaceVariant = Color(0xFF2C1932),
            onSurfaceVariant = Color(0xFFFF85A2)
        ),
        gradientTop = Color(0xFF25152B),
        gradientBottom = Color(0xFF0A050B),
        isDarkBg = true,
        closedContainerBg = Color(0xFFE1306C),
        closedContainerTextColor = Color.White
    )

    val pinterestPalette = PaletteInfo(
        id = "pinterest",
        name = "Pinterest",
        desc = "Pinterest Crimson & Cream Canvas",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFFE60023), Color(0xFF8A0015), Color(0xFFFFF8F8)),
        colorScheme = lightColorScheme(
            primary = Color(0xFFE60023),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFDAD9),
            onPrimaryContainer = Color(0xFF410005),
            secondary = Color(0xFFC0001D),
            secondaryContainer = Color(0xFFFFE3E1),
            onSecondaryContainer = Color(0xFF3B0006),
            background = Color(0xFFFFF8F8),
            onBackground = Color(0xFF2B0005),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF2B0005),
            surfaceVariant = Color(0xFFFFECEB),
            onSurfaceVariant = Color(0xFF900014)
        ),
        gradientTop = Color(0xFFFFF0F0),
        gradientBottom = Color(0xFFFFDAD9),
        isDarkBg = false,
        closedContainerBg = Color(0xFFE60023),
        closedContainerTextColor = Color.White
    )

    val redditPalette = PaletteInfo(
        id = "reddit",
        name = "Reddit",
        desc = "Reddit Orange & Light Slate Canvas",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFFFF4500), Color(0xFFCC3700), Color(0xFFF2F4F5)),
        colorScheme = lightColorScheme(
            primary = Color(0xFFFF4500),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFDBCF),
            onPrimaryContainer = Color(0xFF3B0B00),
            secondary = Color(0xFFCC3700),
            secondaryContainer = Color(0xFFFFE0D6),
            onSecondaryContainer = Color(0xFF451000),
            background = Color(0xFFF2F4F5),
            onBackground = Color(0xFF1C1D1F),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF1C1D1F),
            surfaceVariant = Color(0xFFE2E4E6),
            onSurfaceVariant = Color(0xFF57585A)
        ),
        gradientTop = Color(0xFFFFFFFF),
        gradientBottom = Color(0xFFF2F4F5),
        isDarkBg = false,
        closedContainerBg = Color(0xFFFF4500),
        closedContainerTextColor = Color.White
    )

    val soundcloudPalette = PaletteInfo(
        id = "soundcloud",
        name = "SoundCloud",
        desc = "SoundCloud Sunset Orange & Deep Charcoal",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFFFF5500), Color(0xFFFF8800), Color(0xFF12100E)),
        colorScheme = darkColorScheme(
            primary = Color(0xFFFF5500),
            onPrimary = Color.White,
            primaryContainer = Color(0xFF802B00),
            onPrimaryContainer = Color(0xFFFFDCCF),
            secondary = Color(0xFFFF8800),
            secondaryContainer = Color(0xFF5E3200),
            onSecondaryContainer = Color(0xFFFFE8D1),
            background = Color(0xFF12100E),
            onBackground = Color(0xFFFAF7F5),
            surface = Color(0xFF1C1A17),
            onSurface = Color(0xFFFAF7F5),
            surfaceVariant = Color(0xFF2A2622),
            onSurfaceVariant = Color(0xFFFF9E66)
        ),
        gradientTop = Color(0xFF24201D),
        gradientBottom = Color(0xFF0A0908),
        isDarkBg = true,
        closedContainerBg = Color(0xFFFF5500),
        closedContainerTextColor = Color.White
    )

    val tiktokPalette = PaletteInfo(
        id = "tiktok",
        name = "TikTok",
        desc = "TikTok Pink, Cyber Blue & Obsidian",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFFFE2C55), Color(0xFF0075DB), Color(0xFF050505)),
        colorScheme = darkColorScheme(
            primary = Color(0xFFFE2C55),
            onPrimary = Color.White,
            primaryContainer = Color(0xFF5C0018),
            onPrimaryContainer = Color(0xFFFFD9DF),
            secondary = Color(0xFF0075DB),
            secondaryContainer = Color(0xFF003B73),
            onSecondaryContainer = Color(0xFFD6E8FF),
            background = Color(0xFF050505),
            onBackground = Color(0xFFF1F1F1),
            surface = Color(0xFF121212),
            onSurface = Color(0xFFF1F1F1),
            surfaceVariant = Color(0xFF1F1F1F),
            onSurfaceVariant = Color(0xFFFE2C55)
        ),
        gradientTop = Color(0xFF181818),
        gradientBottom = Color(0xFF020202),
        isDarkBg = true,
        closedContainerBg = Color(0xFFFE2C55),
        closedContainerTextColor = Color.White
    )

    val tumblrPalette = PaletteInfo(
        id = "tumblr",
        name = "Tumblr",
        desc = "Tumblr Deep Navy Blue & Sky Accent",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFF00B8FF), Color(0xFF001935), Color(0xFF002B5B)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF00B8FF),
            onPrimary = Color(0xFF00344D),
            primaryContainer = Color(0xFF004C6D),
            onPrimaryContainer = Color(0xFFCBECFF),
            secondary = Color(0xFF0084FF),
            secondaryContainer = Color(0xFF002B5B),
            onSecondaryContainer = Color(0xFFD1E4FF),
            background = Color(0xFF001935),
            onBackground = Color(0xFFF0F6FF),
            surface = Color(0xFF00244C),
            onSurface = Color(0xFFF0F6FF),
            surfaceVariant = Color(0xFF003163),
            onSurfaceVariant = Color(0xFF80D4FF)
        ),
        gradientTop = Color(0xFF00244C),
        gradientBottom = Color(0xFF001226),
        isDarkBg = true,
        closedContainerBg = Color(0xFF00B8FF),
        closedContainerTextColor = Color(0xFF001935)
    )

    val vinePalette = PaletteInfo(
        id = "vine",
        name = "Vine",
        desc = "Vine Emerald Green, Mint & Clean Canvas",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFF00B488), Color(0xFF00D9A3), Color(0xFFF6F5F6)),
        colorScheme = lightColorScheme(
            primary = Color(0xFF00B488),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFC7F8EC),
            onPrimaryContainer = Color(0xFF00382A),
            secondary = Color(0xFF00D9A3),
            secondaryContainer = Color(0xFFD4FAF0),
            onSecondaryContainer = Color(0xFF004332),
            background = Color(0xFFF6F5F6),
            onBackground = Color(0xFF1A1F1D),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF1A1F1D),
            surfaceVariant = Color(0xFFE2E7E5),
            onSurfaceVariant = Color(0xFF006B51)
        ),
        gradientTop = Color(0xFFFFFFFF),
        gradientBottom = Color(0xFFF6F5F6),
        isDarkBg = false,
        closedContainerBg = Color(0xFF00B488),
        closedContainerTextColor = Color.White
    )

    val xPalette = PaletteInfo(
        id = "x",
        name = "X (Twitter)",
        desc = "Pitch Black, High-Contrast White & Graphite",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFFE7E9EA), Color(0xFF1D9BF0), Color(0xFF000000)),
        colorScheme = darkColorScheme(
            primary = Color(0xFF1D9BF0),
            onPrimary = Color.White,
            primaryContainer = Color(0xFF004070),
            onPrimaryContainer = Color(0xFFD6EEFF),
            secondary = Color(0xFFE7E9EA),
            secondaryContainer = Color(0xFF202327),
            onSecondaryContainer = Color(0xFFE7E9EA),
            background = Color(0xFF000000),
            onBackground = Color(0xFFE7E9EA),
            surface = Color(0xFF16181C),
            onSurface = Color(0xFFE7E9EA),
            surfaceVariant = Color(0xFF202327),
            onSurfaceVariant = Color(0xFF71767B)
        ),
        gradientTop = Color(0xFF1A1C20),
        gradientBottom = Color(0xFF000000),
        isDarkBg = true,
        closedContainerBg = Color(0xFF1D9BF0),
        closedContainerTextColor = Color.White
    )

    val youtubePalette = PaletteInfo(
        id = "youtube",
        name = "YouTube",
        desc = "YT Crimson Red & Light Whitish Gray Canvas",
        group = PaletteGroup.SITE,
        swatches = listOf(Color(0xFFFF0000), Color(0xFFCC0000), Color(0xFFF9F9F9)),
        colorScheme = lightColorScheme(
            primary = Color(0xFFFF0000),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFDAD6),
            onPrimaryContainer = Color(0xFF410002),
            secondary = Color(0xFFCC0000),
            secondaryContainer = Color(0xFFFFE0DF),
            onSecondaryContainer = Color(0xFF380001),
            background = Color(0xFFF9F9F9),
            onBackground = Color(0xFF0F0F0F),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF0F0F0F),
            surfaceVariant = Color(0xFFE5E5E5),
            onSurfaceVariant = Color(0xFF606060)
        ),
        gradientTop = Color(0xFFFFFFFF),
        gradientBottom = Color(0xFFF9F9F9),
        isDarkBg = false,
        closedContainerBg = Color(0xFFFF0000),
        closedContainerTextColor = Color.White
    )

    // Master map of all available palettes
    val allPalettesMap: Map<String, PaletteInfo> = listOf(
        // Custom Group
        cobaltPalette, coralPalette, lavenderPalette, morningPalette,
        oceanPalette, rosesPalette, saharaPalette, woodlandsPalette,
        // Site Group
        blueskyPalette, deviantArtPalette, facebookPalette, flickrPalette,
        imgurPalette, instagramPalette, pinterestPalette,
        redditPalette, soundcloudPalette, tiktokPalette, tumblrPalette,
        vinePalette, xPalette, youtubePalette
    ).associateBy { it.id }

    fun getPaletteById(id: String): PaletteInfo {
        return allPalettesMap[id] ?: when (id) {
            "clean", "dark_slate" -> cobaltPalette
            "periwinkle", "slate_blue" -> PaletteInfo(
                id = "slate_blue",
                name = "Slate Blue",
                desc = "Soft Periwinkle & Slate Canvas",
                group = PaletteGroup.CUSTOM,
                swatches = listOf(Color(0xFF5B67CA), Color(0xFF3B82F6), Color(0xFF1E293B)),
                colorScheme = darkColorScheme(
                    primary = Color(0xFF818CF8),
                    onPrimary = Color(0xFF1E1B4B),
                    primaryContainer = Color(0xFF3730A3),
                    onPrimaryContainer = Color(0xFFE0E7FF),
                    secondary = Color(0xFF6366F1),
                    secondaryContainer = Color(0xFF1E293B),
                    onSecondaryContainer = Color(0xFFEEF2FF),
                    background = Color(0xFF0F172A),
                    onBackground = Color(0xFFF8FAFC),
                    surface = Color(0xFF1E293B),
                    onSurface = Color(0xFFF8FAFC),
                    surfaceVariant = Color(0xFF334155),
                    onSurfaceVariant = Color(0xFFA5B4FC)
                ),
                gradientTop = Color(0xFF1E293B),
                gradientBottom = Color(0xFF090D16),
                isDarkBg = true,
                closedContainerBg = Color(0xFF6366F1),
                closedContainerTextColor = Color.White
            )
            "purple", "midnight", "soft_purple" -> lavenderPalette
            "sunset" -> PaletteInfo(
                id = "sunset",
                name = "Sunset Glow",
                desc = "Warm Sunset Orange & Amber Light",
                group = PaletteGroup.CUSTOM,
                swatches = listOf(Color(0xFFEA580C), Color(0xFFF59E0B), Color(0xFFFFF7ED)),
                colorScheme = lightColorScheme(
                    primary = Color(0xFFEA580C),
                    onPrimary = Color.White,
                    primaryContainer = Color(0xFFFFEDD5),
                    onPrimaryContainer = Color(0xFF7C2D12),
                    secondary = Color(0xFFF59E0B),
                    secondaryContainer = Color(0xFFFEF3C7),
                    onSecondaryContainer = Color(0xFF78350F),
                    background = Color(0xFFFFF7ED),
                    onBackground = Color(0xFF431407),
                    surface = Color(0xFFFFFFFF),
                    onSurface = Color(0xFF431407),
                    surfaceVariant = Color(0xFFFFEDD5),
                    onSurfaceVariant = Color(0xFF9A3412)
                ),
                gradientTop = Color(0xFFFFF3E6),
                gradientBottom = Color(0xFFFFE0BA),
                isDarkBg = false,
                closedContainerBg = Color(0xFFEA580C),
                closedContainerTextColor = Color.White
            )
            else -> cobaltPalette
        }
    }

    /**
     * Builds the ordered list of PaletteInfos according to the rules:
     * Group 1: The current active palette
     * Group 2: Site palettes sorted A->Z (excluding active)
     * Group 3: Custom palettes sorted A->Z (excluding active)
     */
    fun buildGroupedList(activePaletteId: String): List<PaletteInfo> {
        val active = getPaletteById(activePaletteId)

        val sitePalettes = allPalettesMap.values
            .filter { it.group == PaletteGroup.SITE && it.id != active.id }
            .sortedBy { it.name }

        val customPalettes = allPalettesMap.values
            .filter { it.group == PaletteGroup.CUSTOM && it.id != active.id }
            .sortedBy { it.name }

        // Construct the 3 groups sequentially: Group 1 (Active), Group 2 (Sites), Group 3 (Custom)
        return listOf(active) + sitePalettes + customPalettes
    }
}
