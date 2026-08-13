# Project Instructions & Conventions

Refer to [AGENTS.md](./AGENTS.md) for full project rules and instructions.

## Key Rules
- **Package Name**: `com.kcmitch.gallery-dl`
- **Main Files**: `app/src/main/java/com/kcmitch/gallery-dl/MainActivity.kt` & `AppConfig.kt`
- **Sites Directory**: `app/src/main/java/com/kcmitch/gallery-dl/sites/SiteName.kt`
- **XML Elements**: `app/src/main/res/elements/Filename.xml`
- **Photos Directory**: `app/src/main/res/images/` (Priority: `.webp` > `.jpeg` / `.png` / `.svg`)
- **Lean MainActivity**: Keep `MainActivity.kt` slim.
- **Git Sync**: Commit & Push to `main` at `https://github.com/RaptorJesus67/gallery-dl-android` after every change.
- **Site Files**: Maintain a distinct Kotlin file per supported gallery-dl site.
- **AppConfig**: Store AdMob, Play Console Info, App Name, testMode, etc. in `AppConfig.kt` (ignored in `.gitignore`).
