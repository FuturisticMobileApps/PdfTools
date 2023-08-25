package com.futuristicmobileapps.itext.util

import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

suspend fun imagesToPdfImpl(fileName: String, imagePaths: List<String>): Flow<Result> = flow {

    try {

        // open document
        val pdfFileName = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

        val outputStream = withContext(Dispatchers.IO) { FileOutputStream(pdfFileName) }
        val document = Document(PageSize.A4)
        val pdfWriter = PdfWriter.getInstance(document, outputStream)
        document.open()

        // Adjust image
        for (imagePath in imagePaths) {
            val image = Image.getInstance(imagePath)
            image.alignment = Element.ALIGN_CENTER

            // Adjust the image size as needed
            val maxWidth = PageSize.A4.width - document.leftMargin() - document.rightMargin()
            if (image.width > maxWidth) {
                val aspectRatio = image.width / image.height
                val newHeight = maxWidth / aspectRatio
                image.scaleToFit(maxWidth, newHeight)
            }

            document.add(image)
            document.newPage()
        }



        document.close()
        pdfWriter.close()
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

fun createPdfFromImages2(imagePaths: List<String>, outputUri: String) {
    val document = Document(PageSize.A4)
    val outputStream = FileOutputStream(outputUri)
    val writer = PdfWriter.getInstance(document, outputStream)

    document.open()

    for (imagePath in imagePaths) {
        val image = Image.getInstance(imagePath)
        image.alignment = Image.ALIGN_CENTER
        image.scaleToFit(document.pageSize.width - 50, document.pageSize.height - 50)
        document.add(image)
        document.newPage()
    }

    document.close()
    writer.close() // Close the PdfWriter
}


fun createPdfFromImages(imagePaths: List<String>, outputUri: String) {
    val document = Document(PageSize.A4)
    val outputStream = FileOutputStream(outputUri)
    val writer = PdfWriter.getInstance(document, outputStream)

    document.open()

    for (imagePath in imagePaths) {
        val image = Image.getInstance(imagePath)
        image.alignment = Image.ALIGN_CENTER
        image.scaleToFit(document.pageSize.width - 50, document.pageSize.height - 50)
        document.add(image)
        document.newPage()
    }

    document.close()
}


fun convertImagesToPDF(outputPath: String, imagePaths: List<String>) {
    val document = Document(PageSize.A4)
    val pdfWriter = PdfWriter.getInstance(document, FileOutputStream(outputPath))
    document.open()

    for (imagePath in imagePaths) {
        val image = Image.getInstance(imagePath)
        image.alignment = Element.ALIGN_CENTER

        // Adjust the image size as needed
        val maxWidth = PageSize.A4.width - document.leftMargin() - document.rightMargin()
        if (image.width > maxWidth) {
            val aspectRatio = image.width / image.height
            val newWidth = maxWidth
            val newHeight = newWidth / aspectRatio
            image.scaleToFit(newWidth, newHeight)
        }

        document.add(image)
        document.newPage()
    }

    document.close()
}
