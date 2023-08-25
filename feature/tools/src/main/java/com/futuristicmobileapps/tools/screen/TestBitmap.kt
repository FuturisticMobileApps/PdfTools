package com.futuristicmobileapps.tools.screen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun SignatureCanvas(
    bitmapWidth: Int,
    bitmapHeight: Int,
    onBitmapReady: (Bitmap) -> Unit
) {
    var path by remember { mutableStateOf(mutableListOf<Offset>()) }
    var scaleFactor by remember { mutableStateOf(1f) }

    Canvas(
        modifier = Modifier.background(color = Color.Red)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scaleFactor *= zoom
                    path.add(pan)
                }
            }
    ) {
        val paint = Paint().apply {
            color = 0x000000 // black color
            strokeWidth = 5f
            isAntiAlias = true
        }

        onBitmapReady(drawToBitmap(bitmapWidth, bitmapHeight) {
            drawSignaturePath(path, paint)
        })
    }
}

fun DrawScope.drawSignaturePath(path: List<Offset>, paint: Paint) {
    if (path.isEmpty()) return

    drawIntoCanvas { canvas ->
        canvas.nativeCanvas.drawColor(0xFFFFFF)
        for (i in 1 until path.size) {
            canvas.nativeCanvas.drawLine(
                path[i - 1].x, path[i - 1].y,
                path[i].x, path[i].y,
                paint
            )
        }
    }
}


fun drawToBitmap(
    width: Int,
    height: Int,
    drawContent: DrawScope.(Canvas) -> Unit
): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
   /* Canvas(bitmap).apply {
        drawContent()
    }*/
    return bitmap
}
