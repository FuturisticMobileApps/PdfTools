package com.futuristicmobileapps.tools.util.file

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.futuristicmobileapps.itext.ITextConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

suspend fun decodeFileAsync(
    context: Context,
    uri: Uri?,
    streamer: Streamer = FileStreamer()
): FileMeta? {
    return withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver
        var pfd: ParcelFileDescriptor? = null

        try {
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
            e.printStackTrace()
            null
        } finally {
            pfd?.close()
        }
    }
}

@SuppressLint("Range")
private fun getFileMeta(
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


// Extension function to convert Uri to File in a background coroutine
suspend fun Uri.uriToFileAsync(context: Context): File? = withContext(Dispatchers.IO) {
    var file: File? = null
    try {
        context.contentResolver.query(this@uriToFileAsync, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val displayName =
                    cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                file = File(context.cacheDir, displayName)
                context.contentResolver.openInputStream(this@uriToFileAsync)?.use { inputStream ->
                    file?.outputStream()?.use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return@withContext file
}

suspend fun getRealFilePathFromUri(context: Context, uri: Uri): String? =
    withContext(Dispatchers.IO) {
        try {
            val contentResolver: ContentResolver = context.contentResolver
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex: Int = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (columnIndex != -1) {
                        return@withContext it.getString(columnIndex)
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }


fun Uri.uriToFile(context: Context): File? {
    var file: File? = null
    try {
        context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val displayName =
                    cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                file = File(
                    context.cacheDir,
                    displayName
                ) // You can change the destination directory as needed
                context.contentResolver.openInputStream(this)?.use { inputStream ->
                    file?.outputStream()?.use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return file
}


fun String.validateFileName(): String {

    var newFileName = this

    val files = File(ITextConstants.fileDirectory.path).listFiles()

    var counter = 1

    while (files.any { it.name == newFileName + ITextConstants.pdf }) {
        newFileName = "$this($counter)"
        counter++
    }

    return newFileName
}
