package com.futuristicmobileapps.itext.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.ImageRenderInfo
import com.itextpdf.text.pdf.parser.PdfImageObject
import com.itextpdf.text.pdf.parser.PdfReaderContentParser
import com.itextpdf.text.pdf.parser.RenderListener
import com.itextpdf.text.pdf.parser.TextRenderInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


suspend fun Context.extractImages(folderName: String, fileUri: Uri): Flow<Result> =
    flow {

        try {

            val fileDirectory = File("${ITextConstants.fileDirectory}/$folderName")

            if (!fileDirectory.exists())
                fileDirectory.mkdirs()

            val inputStream = contentResolver.openInputStream(fileUri)

            val pdfReader = PdfReader(inputStream)

            val parser = PdfReaderContentParser(pdfReader)

            for (page in 1..pdfReader.numberOfPages) {
                val listener = ImageExtractionListener(folderName)
                parser.processContent(page, listener)
            }

            pdfReader.close()

            withContext(Dispatchers.IO) {
                inputStream?.close()
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


class ImageExtractionListener(private val fileName: String) : RenderListener {
    override fun renderImage(imageRenderInfo: ImageRenderInfo) {
        try {
            val imageObject: PdfImageObject = imageRenderInfo.image
            val imageBytes: ByteArray = imageObject.imageAsBytes

            // Save the image bytes to a file
            val page = imageRenderInfo.ref.number
            val imageFileName = "$fileName${System.currentTimeMillis()}+image_$page.png"


            val imageFile = File(
                "${ITextConstants.fileDirectory}/$fileName",
                imageFileName
            )

            FileOutputStream(imageFile).use { outputStream ->
                outputStream.write(imageBytes)
                outputStream.flush()
                outputStream.close()
            }

        } catch (e: IOException) {
            Log.i("TAG", "renderImage: ${e.message}")
        } catch (e: Exception) {
            Log.i("TAG", "renderImage: ${e.message}")
        }
    }

    override fun beginTextBlock() {}
    override fun renderText(textRenderInfo: TextRenderInfo) {}
    override fun endTextBlock() {}
}

