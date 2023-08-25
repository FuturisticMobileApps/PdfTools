package com.futuristicmobileapps.tools.util

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


val singlePdfLauncherIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
    addCategory(Intent.CATEGORY_OPENABLE)
    type = "application/pdf"
}

val singleDocLauncherIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
    addCategory(Intent.CATEGORY_OPENABLE)
    type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
}

val multiplePdfLauncherIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
    type = "*/*"
    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
}


@Composable
fun pickPdfLauncher(pickedPdf: (pdfFile: PdfFile) -> Unit): ActivityResultLauncher<Intent> =
    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.data?.let { uri ->
            pickedPdf(PdfFile(uri = uri))
            return@rememberLauncherForActivityResult
        }
        pickedPdf(PdfFile(errorMessage = ErrorMessages.NoFilesPicked))
    }

@Composable
fun pickMultiplePdfsLauncher(pickedPdf: (pdfFile: PdfFile) -> Unit): ActivityResultLauncher<Intent> =
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




