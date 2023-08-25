package com.futuristicmobileapps.tools.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun Context.convertPdfToBitmapsAsync(uri: Uri): MutableList<PdfPage> =
    withContext(Dispatchers.IO) {
        val pdfPagesList = mutableListOf<PdfPage>()
        try {
            val fileDescriptor = contentResolver.openFileDescriptor(uri, "r")
            fileDescriptor?.use {
                val pdfRenderer = PdfRenderer(fileDescriptor)

                for (pageIndex in 0 until pdfRenderer.pageCount) {
                    val page = pdfRenderer.openPage(pageIndex)

                    val bitmap =
                        Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)

                    page.render(
                        bitmap,
                        null,
                        null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                    )

                    pdfPagesList.add(PdfPage(bitmap, pageIndex + 1))

                    page.close()
                }

                pdfRenderer.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext pdfPagesList
    }


fun Context.convertPdfToBitmaps(uri: Uri): MutableList<PdfPage> {

    val pdfPagesList = mutableListOf<PdfPage>()

    try {

        val fileDescriptor =
            contentResolver.openFileDescriptor(uri, "r")

        fileDescriptor?.let {

            val pdfRenderer = PdfRenderer(fileDescriptor)

            for (pageIndex in 0 until pdfRenderer.pageCount) {

                val page = pdfRenderer.openPage(pageIndex)

                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                pdfPagesList.add(PdfPage(bitmap, pageIndex))

                page.close()

            }

            pdfRenderer.close()

            fileDescriptor.close()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return pdfPagesList
}

fun  MutableList<PdfPage>.removeDuplicateBitmaps(): MutableList<PdfPage> {
    val uniqueBitmaps = mutableListOf<PdfPage>()
    val duplicateIndexes = mutableListOf<Int>()

    for ((index, pdfPage) in withIndex()) {
        if (!uniqueBitmaps.any { it.bitmap.sameAs(pdfPage.bitmap) }) {
            uniqueBitmaps.add(pdfPage)
        } else {
            duplicateIndexes.add(index)
        }
    }

    for (index in duplicateIndexes.reversed()) {
        removeAt(index)
    }

    return this
}

fun MutableList<PdfPage>.getSortedList(): MutableList<PdfPage> {
    val duplicateBitmaps = mutableListOf<PdfPage>()
    for (pdfPage1 in this) {
        for (pdfPage2 in this) {
            if (pdfPage1 != pdfPage2 && pdfPage1.bitmap.sameAs(pdfPage2.bitmap)) {
                duplicateBitmaps.add(pdfPage1)
            }
        }
    }
    if (duplicateBitmaps.size > 1){
        val firstElement = duplicateBitmaps.first()
        removeAll(duplicateBitmaps)
        this.add(firstElement)
    }

    return this
}

fun Bitmap.rotateBitmap(angle: Int): Bitmap = Bitmap.createBitmap(
    this,
    0,
    0,
    width,
    height,
    Matrix().apply {
        postRotate(angle.toFloat())
    },
    true
)

fun Int.getRotationalAngle(angle: Int): Int =
    if (this > 0 && this == 270) 0 else if (this < 0 && this == -270) 0 else this + angle


@Composable
fun PickPdf(pickedPdf: (pdfFile: PdfFile) -> Unit) {

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"
    }


    val pdfLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.data?.let { uri ->
                pickedPdf(PdfFile(uri = uri))
                return@rememberLauncherForActivityResult
            }
            pickedPdf(PdfFile(errorMessage = ErrorMessages.NoFilesPicked))
        }


    pdfLauncher.launch(intent)

}

@Composable
fun PickMultiplePdfs(pickedPdf: (pdfFile: PdfFile) -> Unit) {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "*/*"
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }

    val pdfListLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            result.data?.clipData?.let { clipData ->

                val uriList = mutableListOf<Uri>()

                if (clipData.itemCount == 1) {
                    pickedPdf(PdfFile(errorMessage = ErrorMessages.SelectMultipleFile))
                }

                for (i in 0 until clipData.itemCount) {
                    uriList.add(clipData.getItemAt(i).uri)
                }

                pickedPdf(PdfFile(uriList = uriList))

                return@rememberLauncherForActivityResult
            }

            pickedPdf(PdfFile(errorMessage = ErrorMessages.NoFilesPicked))
        }
}



