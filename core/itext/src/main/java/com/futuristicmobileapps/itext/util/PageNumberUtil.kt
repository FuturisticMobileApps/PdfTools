package com.futuristicmobileapps.itext.util

import android.content.Context
import android.net.Uri
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfImportedPage
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


suspend fun Context.addPageNumber(fileName: String, uri: Uri): Flow<Result> = flow {

    try {

        // open document
        val invertPdfFile = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

        val outputStream = withContext(Dispatchers.IO) {
            FileOutputStream(invertPdfFile)
        }

        val document = Document(PageSize.A4)
        val pdfWriter = PdfWriter.getInstance(document, outputStream)

        // Attach the PageNumberHandler to the PdfWriter
        val eventHandler = PageNumberHandler()
        pdfWriter.pageEvent = eventHandler

        document.open()

        val inputStream = contentResolver.openInputStream(uri)
        val pdfReader = PdfReader(inputStream)
        val pageNumbers = pdfReader.numberOfPages

        for (pageNumber in 1 .. pageNumbers) {
            val pdfImportedPage: PdfImportedPage = pdfWriter.getImportedPage(pdfReader, pageNumber)
            // Add each page of the input PDF to the new PDF
            val contentByte = pdfWriter.directContent
            contentByte.addTemplate(pdfImportedPage, 0f, 0f)
            document.newPage()
        }


        // close document
        document.close()
        pdfReader.close()
        pdfWriter.close()
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


class PageNumberHandler : PdfPageEventHelper() {

    private var pageNumber = 0

    override fun onEndPage(writer: PdfWriter?, document: Document?) {
        pageNumber++
        val cb: PdfContentByte = writer!!.directContent
        val pageSize = document!!.pageSize
        val font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED)
        cb.beginText()
        cb.setFontAndSize(font, 12f)
        cb.setColorFill(BaseColor.BLACK)
        cb.showTextAligned(
            Element.ALIGN_CENTER,
            "Page $pageNumber",
            pageSize.width/2,
            30f,
            0f
        )
        cb.endText()
    }
}


/*
class PageNumberHandler : PdfPageEventHelper() {

    private val pageNumberFont: Font = Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL)

    override fun onEndPage(writer: PdfWriter, document: Document) {
        val pageNumber = writer.pageNumber
        val phrase = Phrase("Page $pageNumber", pageNumberFont)

        val x = (document.right() - document.left()) / 2 + document.leftMargin()
        val y = document.bottomMargin() - 10

        ColumnText.showTextAligned(writer.directContent, Element.ALIGN_CENTER, phrase, x, y, 0f)
    }
}
*/












