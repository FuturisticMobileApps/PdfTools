package com.futuristicmobileapps.itext.util

import android.content.Context
import android.net.Uri
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfSmartCopy
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

suspend fun Context.compressPdf(
    fileName: String,
    fileUri: Uri,
    compressionLevel: Int
): Flow<Result> =
    flow {

        try {

            val compressFile = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

            val outputStream = withContext(Dispatchers.IO) { FileOutputStream(compressFile) }

            val inputStream = contentResolver.openInputStream(fileUri)

            val pdfReader = PdfReader(inputStream)
            val compressedDocument = Document()
            val pdfSmartCopy = PdfSmartCopy(compressedDocument, outputStream)
            pdfSmartCopy.compressionLevel = 9
            compressedDocument.open()

            for (i in 1..pdfReader.numberOfPages) {
                val importedPage = pdfSmartCopy.getImportedPage(pdfReader, i)
                pdfSmartCopy.addPage(importedPage)
            }

            compressedDocument.close()
            pdfReader.close()

            withContext(Dispatchers.IO) {
                inputStream?.close()
                outputStream.close()
            }

            emit(Result(message = Messages.PageNumberSuccess, status = Status.SUCCESS))

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
