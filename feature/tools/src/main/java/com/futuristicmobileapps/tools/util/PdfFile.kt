package com.futuristicmobileapps.tools.util

import android.net.Uri

data class PdfFile(
    val uri: Uri? = null,
    val uriList: MutableList<Uri>? = null,
    val errorMessage: String? = null,
)
