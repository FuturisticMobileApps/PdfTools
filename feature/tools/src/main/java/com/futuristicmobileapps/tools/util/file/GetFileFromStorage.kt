package com.futuristicmobileapps.tools.util.file

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


 fun decodeFile(
    context: Context,
    uri: Uri?,
    streamer: Streamer = FileStreamer()
): FileMeta? {

    val contentResolver = context.contentResolver

    var pfd: ParcelFileDescriptor? = null
    return try {
        uri?.let { pdfUri ->
            pfd = contentResolver?.openFileDescriptor(pdfUri, "r")
            val inputStream = FileInputStream(pfd?.fileDescriptor)
            val meta = getFileMeta(context, uri) ?: Pair("file", null)
            val pdfFile = File(context.cacheDir, meta.first)
            val outputStream = FileOutputStream(pdfFile)
            streamer.copyFile(inputStream, outputStream)
            FileMeta(meta.first, meta.second, pdfFile)
        }
    } catch (e: Exception) {
        println(e.message)
        null
    } finally {
        pfd?.close()

    }
}

@SuppressLint("Range")
private  fun getFileMeta(
    context: Context,
    uris: Uri?,
): Pair<String, Int?>? {
    return uris?.let { uri ->
        var result: Pair<String, Int?>? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver?.query(uri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val size =
                    cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE)).getFileSize()
                result = Pair(name ?: "file", size)
                cursor.close()
            }
        }
        if (result == null) {
            val path = uri.path
            val cut = path?.lastIndexOf('/')
            if (cut != -1) {
                val name = path?.substring(cut?.plus(1) ?: 0)
                result = Pair(name ?: "file", null)
            }
        }
        result
    }
}


private fun Long?.getFileSize(): Int? {
    return this?.div(1000)?.toInt()
}
