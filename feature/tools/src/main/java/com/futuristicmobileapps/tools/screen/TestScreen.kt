package com.futuristicmobileapps.tools.screen

import android.graphics.Bitmap
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter

@Composable
fun TestScreen() {


}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingCanvas(
    onBitmapReady: (Bitmap) -> Unit
) {
    var drawingPoints by remember { mutableStateOf(emptyList<Offset>()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                if (event.action == MotionEvent.ACTION_DOWN ||
                    event.action == MotionEvent.ACTION_MOVE
                ) {
                    drawingPoints = drawingPoints + Offset(event.x, event.y)
                }
                true
            }
    ) {
        for (i in 1 until drawingPoints.size) {
            drawLine(
                color = Color.Black,
                start = drawingPoints[i - 1],
                end = drawingPoints[i],
                strokeWidth = 5f
            )
        }


        val image = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)

        onBitmapReady(image) // Convert canvas to bitmap
    }
}
