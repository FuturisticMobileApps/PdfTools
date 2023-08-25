package com.futuristicmobileapps.tools.util

import android.graphics.Bitmap

data class PdfPage(
    val bitmap : Bitmap,
    val pageNumber : Int,
    val rotateAngle : Int = 0
)
