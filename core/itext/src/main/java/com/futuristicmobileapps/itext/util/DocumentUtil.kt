package com.futuristicmobileapps.itext.util
import android.content.Context
import android.net.Uri
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


suspend fun Context.docToPdf(fileName: String, fileUri: Uri): Flow<Result> = flow {
    emit(Result(message = Messages.UnknownError))
}






/*
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFPicture
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException



suspend fun Context.docToPdf(fileName: String, fileUri: Uri): Flow<Result> =
    flow {

        try {

            val document = Document()
            val docFile = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(docFile)
            }

            val inputStream = contentResolver.openInputStream(fileUri)

            PdfWriter.getInstance(document, outputStream)
            document.open()

            val xwpfDocument = XWPFDocument(inputStream)
            val paragraphs = xwpfDocument.paragraphs

            for (paragraph in paragraphs) {

                val text = paragraph.text
                document.add(Paragraph(text))  // Use Paragraph to add text

                // Check for images in the paragraph and add them to the PDF
                val runs = paragraph.runs
                for (run in runs) {
                    val pictures = run.embeddedPictures
                    for (picture in pictures) {
                        val xwpfPicture = picture as XWPFPicture
                        val image = Image.getInstance(xwpfPicture.pictureData.data)
                        document.add(image)
                    }
                }
            }


            withContext(Dispatchers.IO) {
                document.close()
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


fun method2() {

    // Load your document file
    val docFilePath = Environment.getExternalStorageDirectory().toString() + "/input_document.docx"
    val docFile = File(docFilePath)

    // Prepare output PDF file
    val pdfFilePath = Environment.getExternalStorageDirectory().toString() + "/output_pdf.pdf"
    val pdfFile = File(pdfFilePath)
    pdfFile.createNewFile()

    // Convert document to PDF
    val document = Document()
    val pdfWriter = PdfWriter.getInstance(document, FileOutputStream(pdfFile))
    document.open()

    try {
        val doc = XWPFDocument(FileInputStream(docFile))
        val paragraphs = doc.paragraphs

        for (paragraph in paragraphs) {
            val text = paragraph.text
            document.add(Paragraph(text))  // Use Paragraph to add text

            // Check for images in the paragraph and add them to the PDF
            val runs = paragraph.runs
            for (run in runs) {
                val pictures = run.embeddedPictures
                for (picture in pictures) {
                    val xwpfPicture = picture as XWPFPicture
                    val image = Image.getInstance(xwpfPicture.pictureData.data)
                    document.add(image)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        document.close()
        pdfWriter.close()
    }
}

*/
