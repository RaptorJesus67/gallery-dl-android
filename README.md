# gallery-dl-android

A modern, high-performance Jetpack Compose graphical user interface and extractor configuration manager for **gallery-dl** on Android devices.

---

## 🚀 Key Features

* **20+ Supported Site Extractors**: Dedicated site definitions for Instagram, Twitter/X, TikTok, Reddit, Pinterest, YouTube, Pixiv, DeviantArt, Tumblr, Danbooru, Imgur, ArtStation, Bluesky, Mastodon, Flickr, Gelbooru, Kemono, SoundCloud, Bilibili, and Weibo.
* **Modular Site Configurations**: Per-site parameters for inclusion types (posts, reels, stories, tweets, manga, audio, attachments, quality settings, and cookie requirements).
* **AppConfig & Git Safety**: Centralized configuration (`AppConfig.kt`) managing AdMob unit IDs, Play Console metadata, default rates, and test mode flags — strictly ignored by Git.
* **Repeatable Design Architecture**: Modular layout blueprints located in `res/elements/` (`button_primary.xml`, `card_container.xml`, `input_field.xml`, `site_card_element.xml`, `terminal_container.xml`).
* **Live Command Preview Terminal**: Real-time CLI syntax output and interactive log terminal simulation.
* **Cookie & Session Editor**: Cookie manager for authenticated downloads on restricted platforms.
* **M3 Material Design & Theme Presets**: Cobalt Blue, Slate Blue, Midnight Purple, Sunset, and TikTok theme styles with linear background accents.

---

## 📁 Repository Structure

```
├── app/
│   ├── src/main/java/
│   │   ├── com/kcmitch/gallery-dl/
│   │   │   ├── MainActivity.kt        # Clean entry point with nested functions
│   │   │   ├── AppConfig.kt           # Central credentials, Play Console info & test mode
│   │   │   └── sites/                 # Site-specific extractor classes
│   │   │       ├── Instagram.kt
│   │   │       ├── Twitter.kt
│   │   │       ├── TikTok.kt
│   │   │       ├── Reddit.kt
│   │   │       └── ... (20 total sites)
│   │   └── com/example/               # Data repositories, ViewModels & Composable UI
│   └── src/main/res/
│       └── elements/                  # Repeatable XML design elements
│           ├── button_primary.xml
│           ├── card_container.xml
│           ├── input_field.xml
│           ├── site_card_element.xml
│           └── terminal_container.xml
├── build.gradle.kts                   # Root Gradle build script
├── settings.gradle.kts                # Root project configuration
├── gradle.properties                  # JVM & Android optimization flags
└── README.md
```

---

## 🛠️ Building & Development

### Prerequisites
* Android Studio Ladybug or newer
* JDK 17+
* Android SDK 35 / Gradle 8.x

### Build Commands

```bash
# Debug APK Build
./gradlew assembleDebug

# Run Unit Tests
./gradlew testDebugUnitTest
```

---

## 🔒 Configuration & Safety

- Sensitive credentials and keys in `AppConfig.kt` are ignored via `.gitignore`.
- Build configurations use Kotlin Symbol Processing (KSP) and modern Jetpack Compose APIs.
