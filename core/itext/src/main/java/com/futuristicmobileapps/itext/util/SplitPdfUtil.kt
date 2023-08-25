package com.futuristicmobileapps.itext.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


suspend fun Context.getNumberOfPagesAsyncFlow(uri: Uri): Flow<Result> = flow {
    val result = try {
        withContext(Dispatchers.IO) {
            val reader = PdfReader(contentResolver.openInputStream(uri))
            val totalPages = reader.numberOfPages
            reader.close()
            Result(
                status = Status.SUCCESS,
                message = "Get page number successfully",
                numberOfPages = totalPages
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result(status = Status.FAILURE, message = "Something went wrong!", numberOfPages = 0)
    }
    emit(result)
}.flowOn(Dispatchers.IO).catch {
    // Handle exceptions that may occur during emission
    emit(Result(status = Status.FAILURE, message = "Something went wrong!", numberOfPages = 0))
}

suspend fun Context.getNumberOfPagesAsync(uri: Uri): Result =
    withContext(Dispatchers.IO) {
        try {
            val reader = PdfReader(contentResolver.openInputStream(uri))
            val totalPages = reader.numberOfPages
            reader.close()
            Log.i("FromNumberOfPages", "getNumberOfPagesAsync: success")
            Result(
                status = Status.SUCCESS,
                message = "Get page number successfully",
                numberOfPages = totalPages
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("FromNumberOfPages", "getNumberOfPagesAsync: failure")
            Result(status = Status.FAILURE)
        }
    }


suspend fun Context.getNumberOfPagesAsync3(uri: Uri): Result =
    withContext(Dispatchers.IO) {
        val mutex = Mutex()
        if (mutex.tryLock()) {
            try {
                val reader = PdfReader(contentResolver.openInputStream(uri))
                val totalPages = reader.numberOfPages
                reader.close()
                Result(
                    status = Status.SUCCESS,
                    message = "Get page number successfully",
                    numberOfPages = totalPages
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Result(status = Status.FAILURE)
            } finally {
                mutex.unlock()
            }
        } else {
            // The function is already running, so just return a failure result.
            Result(status = Status.FAILURE)
        }
    }


suspend fun Context.splitMultiplePdfs(
    fileName: String,
    uri: Uri,
    splitList: MutableList<Pair<Int, Int>>
): Flow<Result> = flow {

    splitList.forEach { pages ->

        emit(
            splitPdf(
                "$fileName${pages.first}_${pages.second}",
                uri,
                pages.first,
                pages.second
            )
        )
    }
}


suspend fun Context.splitPdfItext(
    fileName: String,
    uri: Uri,
    startPage: Int,
    endPage: Int
): Flow<Result> =

    flow {

        emit(
            splitPdf(fileName, uri, startPage, endPage)
        )
    }


fun Context.splitPdf(fileName: String, uri: Uri, startPage: Int, endPage: Int): Result {

    return try {
        // open document
        val splitPdfFile = File(
            ITextConstants.fileDirectory,
            fileName + ITextConstants.pdf
        )

        val outputStream = FileOutputStream(splitPdfFile)
        val document = Document()
        val pdfCopy = PdfCopy(document, outputStream)
        document.open()

        // splitPdf
        val pdfReader = PdfReader(contentResolver.openInputStream(uri))

        for (page in startPage..endPage) {
            val importedPage = pdfCopy.getImportedPage(pdfReader, page)
            pdfCopy.addPage(importedPage)
        }

        pdfReader.close()

        // close document
        pdfCopy.close()
        document.close()
        outputStream.close()


        Result(message = Messages.SplitSuccess, status = Status.SUCCESS)

    } catch (e: IOException) {
        Result(message = Messages.IOException)
    } catch (e: IllegalArgumentException) {
        Result(message = Messages.IOException)
    } catch (e: DocumentException) {
        Result(message = Messages.DocumentException)
    } catch (e: Exception) {
        Result(message = Messages.UnknownError)
    }
}