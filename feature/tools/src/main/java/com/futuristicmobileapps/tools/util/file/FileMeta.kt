package com.futuristicmobileapps.tools.util.file

import java.io.File

data class FileMeta(
    val name: String? = null,
    val sizeKb: Int? = null,
    val file: File? = null
)