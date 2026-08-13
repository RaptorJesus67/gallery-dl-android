package com.kcmitch.gallery_dl.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

/**
 * StackedPhotoVectorGraphic
 * Pure vector implementation of stacked photo frames with sun and mountain line art.
 * Dynamically updates colors based on the current MaterialTheme color scheme.
 */
@Composable
fun StackedPhotoVectorGraphic(
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.tertiary,
    outlineColor: Color = MaterialTheme.colorScheme.outline,
    cardBgColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cardWidth = w * 0.60f
        val cardHeight = h * 0.75f
        val cornerRadius = CornerRadius(14.dp.toPx(), 14.dp.toPx())

        // Back Card (Tilted -16 deg)
        withTransform({
            rotate(-16f, pivot = Offset(w * 0.40f, h * 0.46f))
            translate(left = w * 0.10f, top = h * 0.08f)
        }) {
            drawRoundRect(
                color = cardBgColor.copy(alpha = 0.5f),
                size = Size(cardWidth, cardHeight),
                cornerRadius = cornerRadius
            )
            drawRoundRect(
                color = outlineColor.copy(alpha = 0.35f),
                size = Size(cardWidth, cardHeight),
                cornerRadius = cornerRadius,
                style = Stroke(width = 2.5.dp.toPx())
            )
            drawRoundRect(
                color = outlineColor.copy(alpha = 0.2f),
                topLeft = Offset(cardWidth * 0.08f, cardHeight * 0.07f),
                size = Size(cardWidth * 0.84f, cardHeight * 0.64f),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Middle Card (Tilted -7 deg)
        withTransform({
            rotate(-7f, pivot = Offset(w * 0.46f, h * 0.48f))
            translate(left = w * 0.16f, top = h * 0.10f)
        }) {
            drawRoundRect(
                color = cardBgColor.copy(alpha = 0.8f),
                size = Size(cardWidth, cardHeight),
                cornerRadius = cornerRadius
            )
            drawRoundRect(
                color = outlineColor.copy(alpha = 0.60f),
                size = Size(cardWidth, cardHeight),
                cornerRadius = cornerRadius,
                style = Stroke(width = 3.dp.toPx())
            )
            drawRoundRect(
                color = outlineColor.copy(alpha = 0.4f),
                topLeft = Offset(cardWidth * 0.08f, cardHeight * 0.07f),
                size = Size(cardWidth * 0.84f, cardHeight * 0.64f),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Front Main Card (Tilted +4 deg)
        withTransform({
            rotate(4f, pivot = Offset(w * 0.52f, h * 0.50f))
            translate(left = w * 0.22f, top = h * 0.12f)
        }) {
            // Main card frame fill & outline
            drawRoundRect(
                color = cardBgColor,
                size = Size(cardWidth, cardHeight),
                cornerRadius = cornerRadius
            )
            drawRoundRect(
                color = primaryColor,
                size = Size(cardWidth, cardHeight),
                cornerRadius = cornerRadius,
                style = Stroke(width = 3.5.dp.toPx())
            )

            // Inner Image Window
            val innerW = cardWidth * 0.84f
            val innerH = cardHeight * 0.64f
            val innerTopLeft = Offset(cardWidth * 0.08f, cardHeight * 0.07f)

            drawRoundRect(
                color = primaryColor.copy(alpha = 0.12f),
                topLeft = innerTopLeft,
                size = Size(innerW, innerH),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )
            drawRoundRect(
                color = primaryColor,
                topLeft = innerTopLeft,
                size = Size(innerW, innerH),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                style = Stroke(width = 2.5.dp.toPx())
            )

            // Sun / Circle in Inner Window
            val sunCenter = Offset(
                innerTopLeft.x + innerW * 0.32f,
                innerTopLeft.y + innerH * 0.30f
            )
            drawCircle(
                color = secondaryColor,
                radius = innerW * 0.12f,
                center = sunCenter,
                style = Stroke(width = 2.5.dp.toPx())
            )

            // Mountain Line Art
            val mountainPath = Path().apply {
                val startX = innerTopLeft.x + innerW * 0.08f
                val startY = innerTopLeft.y + innerH * 0.90f
                moveTo(startX, startY)
                lineTo(innerTopLeft.x + innerW * 0.38f, innerTopLeft.y + innerH * 0.45f)
                lineTo(innerTopLeft.x + innerW * 0.58f, innerTopLeft.y + innerH * 0.72f)
                lineTo(innerTopLeft.x + innerW * 0.72f, innerTopLeft.y + innerH * 0.55f)
                lineTo(innerTopLeft.x + innerW * 0.92f, startY)
            }
            drawPath(
                path = mountainPath,
                color = primaryColor,
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}
