package com.kcmitch.gallery_dl.pages

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel
import kotlinx.coroutines.launch

/**
 * Terminal Page (Terminal / MS-DOS style CLI console)
 * Features:
 * - Completely black canvas with edge border
 * - Blinking solid block cursor (█) moving dynamically with active cursor selection
 * - Green '~' and White '$' prompt styling
 * - Soft keyboard focus trigger on tap
 */
@Composable
fun TerminalPage(
    viewModel: GalleryDlViewModel,
    modifier: Modifier = Modifier
) {
    val logs by viewModel.downloadLogs.collectAsState()
    var currentInputValue by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Blinking cursor animation (500ms cycle)
    val infiniteTransition = rememberInfiniteTransition(label = "cursor_transition")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )

    val isCursorVisible = cursorAlpha > 0.5f

    val terminalGreen = Color(0xFF22C55E) // Terminal Green
    val terminalWhite = Color(0xFFFFFFFF)

    // Local execution history buffer
    val consoleLines = remember {
        mutableStateListOf(
            "gallery-dl [Android Terminal v1.2.0]",
            "Type 'gallery-dl --help' or enter a URL to execute.",
            "~ $ "
        )
    }

    LaunchedEffect(logs.size) {
        if (logs.isNotEmpty()) {
            listState.animateScrollToItem(logs.size + consoleLines.size)
        }
    }

    // Top border margin allows the natural background of the app to bleed through slightly
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF333333), shape = RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusRequester.requestFocus()
            }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header bar inside Terminal
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "terminal@gallery-dl:~",
                    color = terminalGreen,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "BASH 5.2",
                    color = Color(0xFF64748B),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp
                )
            }

            // Scrollable Terminal Logs & Output
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(consoleLines) { line ->
                    if (line.startsWith("~ $")) {
                        val promptText = buildAnnotatedString {
                            withStyle(SpanStyle(color = terminalGreen, fontWeight = FontWeight.Bold)) {
                                append("~ ")
                            }
                            withStyle(SpanStyle(color = terminalWhite, fontWeight = FontWeight.Bold)) {
                                append("$ ")
                            }
                            append(line.removePrefix("~ $"))
                        }
                        Text(
                            text = promptText,
                            color = terminalWhite,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    } else {
                        Text(
                            text = line,
                            color = if (line.contains("ERROR", true)) Color(0xFFEF4444) else Color(0xFFCBD5E1),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }

                // Append live ViewModel execution logs
                items(logs) { log ->
                    Text(
                        text = "[${log.timestamp}] ${log.message}",
                        color = Color(0xFF22C55E),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }

                // Active Command Line Prompt with Dynamic Moving Block Cursor
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = terminalGreen, fontWeight = FontWeight.Bold)) {
                                    append("~ ")
                                }
                                withStyle(SpanStyle(color = terminalWhite, fontWeight = FontWeight.Bold)) {
                                    append("$ ")
                                }
                            },
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )

                        BasicTextField(
                            value = currentInputValue,
                            onValueChange = { currentInputValue = it },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                color = terminalWhite,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 13.sp
                            ),
                            cursorBrush = SolidColor(Color.Transparent), // Custom block cursor overlay via VisualTransformation
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    val inputStr = currentInputValue.text
                                    if (inputStr.isNotBlank()) {
                                        val cmd = inputStr.trim()
                                        consoleLines.add("~ $ $cmd")
                                        when {
                                            cmd == "clear" -> {
                                                consoleLines.clear()
                                                consoleLines.add("~ $ ")
                                            }
                                            cmd == "help" || cmd == "gallery-dl --help" -> {
                                                consoleLines.add("Usage: gallery-dl [OPTIONS] URL ...")
                                                consoleLines.add("Options:")
                                                consoleLines.add("  -d, --destination PATH   Target download directory")
                                                consoleLines.add("  -u, --username USER     Username for authentication")
                                                consoleLines.add("  -p, --password PASS     Password for authentication")
                                                consoleLines.add("  --cookies FILE          Cookie jar file path")
                                                consoleLines.add("  --rate RATE             Maximum download rate")
                                            }
                                            else -> {
                                                viewModel.runCustomCommand(cmd)
                                            }
                                        }
                                        currentInputValue = TextFieldValue("")
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(consoleLines.size)
                                        }
                                    }
                                }
                            ),
                            visualTransformation = VisualTransformation { annotatedString ->
                                val textStr = annotatedString.text
                                val cursorPos = currentInputValue.selection.start.coerceIn(0, textStr.length)

                                val transformed = buildAnnotatedString {
                                    if (cursorPos < textStr.length) {
                                        // Characters before cursor
                                        if (cursorPos > 0) {
                                            append(textStr.substring(0, cursorPos))
                                        }
                                        // Character under cursor: inverted block color when blinking
                                        withStyle(
                                            SpanStyle(
                                                background = if (isCursorVisible) terminalWhite else Color.Transparent,
                                                color = if (isCursorVisible) Color.Black else terminalWhite
                                            )
                                        ) {
                                            append(textStr[cursorPos])
                                        }
                                        // Characters after cursor
                                        if (cursorPos + 1 < textStr.length) {
                                            append(textStr.substring(cursorPos + 1))
                                        }
                                    } else {
                                        // Cursor is at end of text
                                        append(textStr)
                                        withStyle(
                                            SpanStyle(
                                                background = if (isCursorVisible) terminalWhite else Color.Transparent,
                                                color = if (isCursorVisible) Color.Black else terminalWhite
                                            )
                                        ) {
                                            append(" ")
                                        }
                                    }
                                }

                                val offsetMapping = object : OffsetMapping {
                                    override fun originalToTransformed(offset: Int): Int = offset.coerceIn(0, textStr.length)
                                    override fun transformedToOriginal(offset: Int): Int = offset.coerceIn(0, textStr.length)
                                }

                                TransformedText(transformed, offsetMapping)
                            }
                        )
                    }
                }
            }
        }
    }
}
