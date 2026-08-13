# Project Instructions & Conventions

## Package & File Structure
- **Package Name**: `com.kcmitch.gallery-dl`
- **Main Files**:
  - `app/src/main/java/com/kcmitch/gallery-dl/MainActivity.kt`
  - `app/src/main/java/com/kcmitch/gallery-dl/AppConfig.kt`
- **Supported Sites**:
  - Maintain a separate Kotlin file for each supported site under:
    `app/src/main/java/com/kcmitch/gallery-dl/sites/SiteName.kt` (e.g., `Instagram.kt`, `Twitter.kt`, `TikTok.kt`)
- **Repeatable XML Elements**:
  - Store reusable XML elements (buttons, containers, etc.) under:
    `app/src/main/res/elements/Filename.xml`

## Assets & Photos Strategy
- Priority format for all photos created or uploaded: **WEBP** (`.webp`)
- If `.webp` is not applicable:
  - Use **JPEG** for images that do not require transparent backgrounds.
  - Use **PNG** for images that require transparent backgrounds.
  - Use **SVG** for vector graphics.
- All photo assets must be saved under: `app/src/main/res/images/`

## Core Rules & Mandates
1. **Modular Code**: Create repeatable code elements when possible via dedicated Classes and nested functions.
2. **Minimal MainActivity**: Prevent excess code from being placed in `MainActivity.kt`.
3. **Git Sync**: Commit AND push updates to the `main` branch of the repo after every change at:
   `https://github.com/RaptorJesus67/gallery-dl-android`
4. **Dedicated Site Files**: Maintain a distinct Kotlin file for each supported site on gallery-dl (`Instagram.kt` for instagram.com, `Twitter.kt` for x.com/twitter.com, `TikTok.kt` for tiktok.com, etc.).
5. **XML Layout Design**: Use XML files for repeatable structure and design elements for ease of manual editing.
6. **AppConfig Centralization**: Any common or repeatable source (AdMob, Play Console Info, IDs, App Name, testMode, etc.) must be added to `AppConfig.kt` (which is untracked in `.gitignore`).
