package com.futuristicmobileapps.itext.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfDictionary
import com.itextpdf.text.pdf.PdfName
import com.itextpdf.text.pdf.PdfNumber
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun Context.rotatePdf(fileName: String, uri: Uri, anglesList: List<Int>): Flow<Result> =

    flow {

        try {

            // open document
            val rotatePdfFile = File(
                ITextConstants.fileDirectory,
                fileName + ITextConstants.pdf
            )
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(rotatePdfFile)
            }

            val pdfReader = PdfReader(contentResolver.openInputStream(uri))

            // rotate PDF
            val totalPages = pdfReader.numberOfPages
            var pdfDictionary: PdfDictionary
            var rotate: PdfNumber?

            for (pageNumber in 1..totalPages) {

                pdfDictionary = pdfReader.getPageN(pageNumber)

                rotate = pdfDictionary.getAsNumber(PdfName.ROTATE)

                val rotationAngle = anglesList[pageNumber - 1]

                if (rotate == null)
                    pdfDictionary.put(PdfName.ROTATE, PdfNumber(rotationAngle))
                else
                    pdfDictionary.put(
                        PdfName.ROTATE,
                        PdfNumber((rotate.intValue() + rotationAngle) % 360)
                    )
            }

            val stamper = PdfStamper(pdfReader, outputStream)

            // close document
            stamper.close()
            pdfReader.close()
            withContext(Dispatchers.IO) {
                outputStream.close()
            }

            emit(Result(status = Status.SUCCESS,message = Messages.RotateSuccess))

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
