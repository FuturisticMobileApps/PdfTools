package com.futuristicmobileapps.itext.util


import android.content.Context
import android.net.Uri
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.BaseColor
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfGState
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

suspend fun Context.addWatermarkImage(
    fileName: String,
    fileUri: Uri,
    imageUri: String
): Flow<Result> =
    flow {

        try {

            // open document
            val invertPdfFile = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(invertPdfFile)
            }

            val inputStream = contentResolver.openInputStream(fileUri)
            val pdfReader = PdfReader(inputStream)
            val stamper = PdfStamper(pdfReader, outputStream)

            val watermarkImage = Image.getInstance(imageUri)
//            watermarkImage.scaleToFit(200f, 200f)
            val watermarkWidth = watermarkImage.scaledWidth
            val watermarkHeight = watermarkImage.scaledHeight

            val gState = PdfGState()
            gState.setFillOpacity(0.2f)

            for (pageNumber in 1..pdfReader.numberOfPages) {
                val over = stamper.getOverContent(pageNumber)
                val pageSize = pdfReader.getPageSize(pageNumber)
                val x = (pageSize.width - watermarkWidth) / 2
                val y = (pageSize.height - watermarkHeight) / 2
                over.setGState(gState)
                watermarkImage.setAbsolutePosition(x, y)
                over.addImage(watermarkImage)
            }

            stamper.close()
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


suspend fun Context.addWatermarkText(
    fileName: String,
    fileUri: Uri,
    watermarkText: String
): Flow<Result> =
    flow {

        try {

            // open document
            val invertPdfFile = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(invertPdfFile)
            }

            val inputStream = contentResolver.openInputStream(fileUri)
            val pdfReader = PdfReader(inputStream)
            val stamper = PdfStamper(pdfReader, outputStream)
            // opacity
            val gState = PdfGState()
            gState.setFillOpacity(0.2f)
            //font
            val baseFont =
                BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)
            val font = Font(baseFont, 78f, Font.BOLD, BaseColor.GRAY)
            // rotation
            val rotationAngle = 0f

            for (pageNumber in 1..pdfReader.numberOfPages) {
                val over = stamper.getOverContent(pageNumber)
                val pageSize = pdfReader.getPageSizeWithRotation(pageNumber)
                val x = (pageSize.width) / 2
                val y = (pageSize.height - (font.size/2)) / 2
                over.setGState(gState)
                ColumnText.showTextAligned(
                    over,
                    Element.ALIGN_CENTER,
                    Phrase(watermarkText, font),
                    x,
                    y,
                    rotationAngle
                )
            }

            stamper.close()
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


fun addImageAndTextWatermarkToPdf(
    inputFilePath: String,
    outputFilePath: String,
    watermarkImagePath: String,
    watermarkText: String
) {
    val reader = PdfReader(inputFilePath)
    val stamper = PdfStamper(reader, FileOutputStream(outputFilePath))

    val baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)
    val font = Font(baseFont, 36f, Font.BOLD, BaseColor.GRAY)

    val watermarkImage = Image.getInstance(watermarkImagePath)
    watermarkImage.scaleToFit(200f, 200f)
    val watermarkWidth = watermarkImage.scaledWidth
    val watermarkHeight = watermarkImage.scaledHeight

    for (pageNumber in 1..reader.numberOfPages) {
        val over = stamper.getOverContent(pageNumber)
        val pageSize = reader.getPageSize(pageNumber)

        val xImage = (pageSize.width - watermarkWidth) / 2
        val yImage = (pageSize.height - watermarkHeight) / 2
        watermarkImage.setAbsolutePosition(xImage, yImage)
        over.addImage(watermarkImage)

        val xText = pageSize.width - 200f
        val yText = 100f
        ColumnText.showTextAligned(
            over,
            Element.ALIGN_RIGHT,
            Phrase(watermarkText, font),
            xText,
            yText,
            0f
        )
    }

    stamper.close()
    reader.close()
}


class WatermarkPageEvent3(private val watermarkText: String) : PdfPageEventHelper() {

    private val font = Font(Font.FontFamily.HELVETICA, 36f, Font.BOLD, BaseColor.GRAY)


    override fun onEndPage(writer: PdfWriter?, document: com.itextpdf.text.Document?) {
        val cb = writer?.directContentUnder
        val pageSize = document?.pageSize
        val x = 100f // Adjust the x-coordinate
        val y = pageSize?.height?.minus(100f) ?: 0f // Adjust the y-coordinate

        val phrase = Phrase(watermarkText, font)

        cb?.saveState()
        cb?.beginText()
        cb?.setFontAndSize(font.baseFont, font.size)
        cb?.setColorFill(BaseColor.GRAY)
        cb?.setTextMatrix(x, y)
        cb?.showTextAligned(PdfContentByte.ALIGN_LEFT, watermarkText, x, y, 45f)
        cb?.endText()
        cb?.restoreState()
    }
}

class WatermarkPageEvent(private val watermarkText: String) : PdfPageEventHelper() {

    private val font = Font(Font.FontFamily.HELVETICA, 36f, Font.BOLD, BaseColor.RED)

    override fun onEndPage(writer: PdfWriter?, document: com.itextpdf.text.Document?) {
        val cb = writer?.directContentUnder
        val pageSize = document?.pageSize
        val x = 100f // Adjust the x-coordinate
        val y = pageSize?.height?.minus(100f) ?: 0f // Adjust the y-coordinate

        cb?.saveState()
        cb?.beginText()
        cb?.setFontAndSize(font.baseFont, font.size)
        cb?.setColorFill(BaseColor.GRAY)
        cb?.setTextMatrix(x, y)
        cb?.showTextAligned(PdfContentByte.ALIGN_LEFT, watermarkText, x, y, 45f)
        cb?.endText()
        cb?.restoreState()
    }
}


