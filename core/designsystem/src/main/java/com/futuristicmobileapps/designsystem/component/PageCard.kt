package com.futuristicmobileapps.designsystem.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun PageCard(
    bitmap: Bitmap,
    onCancelClicked: () -> Unit,
    onUpClicked: () -> Unit,
    onDownClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .height(180.dp)
            .background(color = Color.White)
    ) {

        Image(
            bitmap = bitmap.asImageBitmap(),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )

        Row {
            Button(onClick = { onCancelClicked() }) {
                Text(text = "Cancel")
            }

            Button(onClick = { onUpClicked() }) {
                Text(text = "Up")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { onDownClicked() }) {
                Text(text = "Down")
            }
        }
    }
}

@Composable
fun RotatePageCard(
    bitmap: Bitmap,
    pageNumber: Int,
    onLeftClicked: () -> Unit,
    onRightClicked: () -> Unit,
) {

    Box(
        modifier = Modifier
            .height(180.dp)
            .background(color = Color.White)
    ) {

        Image(
            bitmap = bitmap.asImageBitmap(),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )

        Row {

            Button(onClick = { onLeftClicked() }) {
                Text(text = "Left")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { onRightClicked() }) {
                Text(text = "Right")
            }
        }

        Text(text = pageNumber.toString(), modifier = Modifier.align(Alignment.BottomStart))
    }
}


