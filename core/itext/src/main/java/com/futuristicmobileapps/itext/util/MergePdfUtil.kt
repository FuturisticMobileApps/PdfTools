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

suspend fun Context.mergePdfItext(fileName: String, uriLst: MutableList<Uri>): Flow<Result> = flow {

    try {

        // open document
        val mergedPdfFile = File(
            ITextConstants.fileDirectory,
            fileName + ITextConstants.pdf
        )
        val outputStream = withContext(Dispatchers.IO) {
            FileOutputStream(mergedPdfFile)
        }
        val document = Document()
        val pdfCopy = PdfCopy(document, outputStream)
        document.open()

        // merge pdf
        uriLst.forEach { uri ->
            val pdfReader = PdfReader(contentResolver.openInputStream(uri))
            pdfCopy.addDocument(pdfReader)
            pdfReader.close()
        }

        // close document
        pdfCopy.close()
        document.close()
        withContext(Dispatchers.IO) {
            outputStream.close()
        }

        emit(Result(status = Status.SUCCESS,message = Messages.MergeSuccess))

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

