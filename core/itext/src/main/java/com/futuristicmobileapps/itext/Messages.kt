package com.futuristicmobileapps.itext

import android.os.Environment


object ITextConstants {
    const val pdf = ".pdf"
    const val png = ".png"
    val fileDirectory = Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DOCUMENTS}/pdfFiles")
}

object Messages {
    const val FileNotCreated = "can't able to create file!"
    const val MergeSuccess = "Pdf merged successfully"
    const val ReorderSuccess = "Pages reordered"
    const val RotateSuccess = "Pages rotated merged successfully"
    const val SplitSuccess = "Pdf split successfully"
    const val PageNumberSuccess = "Add page numbers successfully"
    const val IOException = "Can't read file"
    const val NotPerform = "Not perform this action"
    const val DocumentException = "Can't read document"
    const val FileEncrypted = "Failed : File is encrypted"
    const val UnknownError = "Failed : Unknown error"
    const val PdfProtected = "PDF is password-protected"
}
