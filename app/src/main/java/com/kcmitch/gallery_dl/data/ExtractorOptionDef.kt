package com.kcmitch.gallery_dl.data

enum class ExtractorValueType {
    STRING, NUMBER, BOOLEAN, JSON_ARRAY
}

data class ExtractorOptionDef(
    val key: String,
    val label: String,
    val defaultValue: String,
    val description: String,
    val valueType: ExtractorValueType = ExtractorValueType.STRING
)

object ExtractorOptionRegistry {
    val defaultOptions = listOf(
        ExtractorOptionDef(
            key = "parent-directory",
            label = "Parent Directory",
            defaultValue = "downloads/",
            description = "Parent directory relative to base download directory",
            valueType = ExtractorValueType.STRING
        ),
        ExtractorOptionDef(
            key = "filename",
            label = "Filename Format",
            defaultValue = "{category}_{id}_{num}.{extension}",
            description = "Formatting string template for saved media files",
            valueType = ExtractorValueType.STRING
        ),
        ExtractorOptionDef(
            key = "directory",
            label = "Subdirectory Format",
            defaultValue = "[\"{category}\", \"{uploader}\"]",
            description = "Subfolder path hierarchy template",
            valueType = ExtractorValueType.JSON_ARRAY
        ),
        ExtractorOptionDef(
            key = "archive",
            label = "Archive File",
            defaultValue = "archive.db",
            description = "SQLite database file recording downloaded media IDs to prevent re-downloads",
            valueType = ExtractorValueType.STRING
        ),
        ExtractorOptionDef(
            key = "sleep",
            label = "Sleep Between Downloads (sec)",
            defaultValue = "0",
            description = "Interval delay between consecutive file downloads in seconds",
            valueType = ExtractorValueType.NUMBER
        ),
        ExtractorOptionDef(
            key = "sleep-request",
            label = "Sleep Between Requests (sec)",
            defaultValue = "0.5",
            description = "Interval delay between HTTP API requests in seconds",
            valueType = ExtractorValueType.NUMBER
        ),
        ExtractorOptionDef(
            key = "mtime",
            label = "Preserve Media Modification Time",
            defaultValue = "true",
            description = "Set local file modification time (mtime) to original post timestamp",
            valueType = ExtractorValueType.BOOLEAN
        ),
        ExtractorOptionDef(
            key = "retries",
            label = "Network Retries",
            defaultValue = "3",
            description = "Number of retry attempts on network error",
            valueType = ExtractorValueType.NUMBER
        ),
        ExtractorOptionDef(
            key = "timeout",
            label = "Network Timeout (sec)",
            defaultValue = "30",
            description = "Socket connection timeout limit in seconds",
            valueType = ExtractorValueType.NUMBER
        ),
        ExtractorOptionDef(
            key = "user-agent",
            label = "Custom User-Agent Header",
            defaultValue = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
            description = "HTTP User-Agent string sent with requests",
            valueType = ExtractorValueType.STRING
        ),
        ExtractorOptionDef(
            key = "api-key",
            label = "Site API Key",
            defaultValue = "",
            description = "API Key or Auth Token required by site extractor",
            valueType = ExtractorValueType.STRING
        ),
        ExtractorOptionDef(
            key = "captions",
            label = "Save Captions / Descriptions",
            defaultValue = "true",
            description = "Save post text content / captions to companion file",
            valueType = ExtractorValueType.BOOLEAN
        ),
        ExtractorOptionDef(
            key = "comments",
            label = "Save Post Comments",
            defaultValue = "false",
            description = "Fetch and store post comments in JSON metadata",
            valueType = ExtractorValueType.BOOLEAN
        ),
        ExtractorOptionDef(
            key = "tags",
            label = "Save Image Tags",
            defaultValue = "true",
            description = "Extract and store image tags",
            valueType = ExtractorValueType.BOOLEAN
        ),
        ExtractorOptionDef(
            key = "path-restrict",
            label = "Path Character Restriction",
            defaultValue = "auto",
            description = "Sanitizes non-standard characters in paths ('auto', 'windows', 'unix')",
            valueType = ExtractorValueType.STRING
        ),
        ExtractorOptionDef(
            key = "skip",
            label = "Skip Existing Files",
            defaultValue = "false",
            description = "Skip downloading if file already exists in download folder",
            valueType = ExtractorValueType.BOOLEAN
        ),
        ExtractorOptionDef(
            key = "postprocessors",
            label = "Post-Processors List",
            defaultValue = "[]",
            description = "Post-processing tasks (e.g. ffmpeg conversion or metadata embedding)",
            valueType = ExtractorValueType.JSON_ARRAY
        )
    )

    fun getDefByKey(key: String): ExtractorOptionDef {
        return defaultOptions.find { it.key == key } ?: ExtractorOptionDef(
            key = key,
            label = key,
            defaultValue = "",
            description = "Custom extractor option '$key'",
            valueType = ExtractorValueType.STRING
        )
    }
}
