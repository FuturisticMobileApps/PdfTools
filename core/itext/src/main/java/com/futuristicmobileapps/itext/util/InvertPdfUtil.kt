package com.futuristicmobileapps.itext.util

import android.content.Context
import android.net.Uri
import android.os.Environment
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


suspend fun Context.invertPdf(fileName: String, uri: Uri): Flow<Result> = flow {

    try {

        // open document
        val invertPdfFile = File(
            ITextConstants.fileDirectory,
            fileName + ITextConstants.pdf
        )
        val outputStream = withContext(Dispatchers.IO) {
            FileOutputStream(invertPdfFile)
        }
        val document = Document()
        val pdfCopy = PdfCopy(document, outputStream)
        document.open()

        // invert
        val pdfReader = PdfReader(contentResolver.openInputStream(uri))

        for (page in pdfReader.numberOfPages downTo 1) {
            val importedPage = pdfCopy.getImportedPage(pdfReader, page)
            pdfCopy.addPage(importedPage)
        }


        pdfReader.close()
        // close document
        pdfCopy.close()
        document.close()
        withContext(Dispatchers.IO) {
            outputStream.close()
        }

        emit(Result(message = Messages.SplitSuccess, status = Status.SUCCESS))

    } catch (e: IOException) {
        emit(Result(message = Messages.IOException))
    } catch (e: IllegalArgumentException) {
        emit(Result(message = Messages.IOException))
    } catch (e: DocumentException) {
        emit(Result(message = Messages.DocumentException))
    } catch (e: Exception) {
        emit(Result(message = Messages.UnknownError))
    }
}