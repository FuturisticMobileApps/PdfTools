package com.futuristicmobileapps.itext.util

import android.content.Context
import android.net.Uri
import com.futuristicmobileapps.itext.ITextConstants
import com.futuristicmobileapps.itext.Messages
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.itextpdf.text.DocumentException
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


suspend fun Context.addPassword(
    fileName: String,
    password: String,
    uri: Uri,
    encryptType: Int = PdfWriter.ENCRYPTION_AES_128,
): Flow<Result> = flow {

    try {


        val invertPdfFile = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

        val outputStream = withContext(Dispatchers.IO) { FileOutputStream(invertPdfFile) }
        val pdfReader = PdfReader(contentResolver.openInputStream(uri))

        if (pdfReader.isEncrypted) {
            return@flow emit(Result(message = Messages.IOException))
        }

        val pdfStamper = PdfStamper(pdfReader, outputStream)

        val permission = PdfWriter.ALLOW_PRINTING or PdfWriter.ALLOW_COPY

        pdfStamper.setEncryption(
            password.toByteArray(),
            "pdftools".toByteArray(),
            permission,
            encryptType
        )

        pdfStamper.close()
        pdfReader.close()
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


suspend fun Context.removePassword(
    fileName: String,
    uri: Uri,
    password: String = "",
    encryptType: Int = PdfWriter.STANDARD_ENCRYPTION_128,
): Flow<Result> = flow {

    try {

        val invertPdfFile = File(ITextConstants.fileDirectory, fileName + ITextConstants.pdf)

        val outputStream = withContext(Dispatchers.IO) { FileOutputStream(invertPdfFile) }
        val pdfReader = if (password.isNotEmpty())
            PdfReader(contentResolver.openInputStream(uri), password.toByteArray())
        else
            PdfReader(contentResolver.openInputStream(uri), "pdftools".toByteArray())

        val pdfStamper = PdfStamper(pdfReader, outputStream)

        val permission = PdfWriter.ALLOW_PRINTING or PdfWriter.ALLOW_COPY

        pdfStamper.setEncryption(
            null,
            null,
            permission,
            encryptType
        )

        pdfStamper.close()
        pdfReader.close()
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


fun removePdfPassword(inputPath: String, outputPath: String) {
    val pdfReader = PdfReader(inputPath)
    val pdfStamper = PdfStamper(pdfReader, FileOutputStream(outputPath))
    pdfStamper.setEncryption(
        null,
        null,
        PdfWriter.ALLOW_PRINTING,
        PdfWriter.STANDARD_ENCRYPTION_128
    )
    pdfStamper.close()
    pdfReader.close()
}

fun addPdfPassword(
    inputPath: String,
    outputPath: String,
    userPassword: String,
    ownerPassword: String
) {
    val pdfReader = PdfReader(inputPath)
    val pdfStamper = PdfStamper(pdfReader, FileOutputStream(outputPath))
    pdfStamper.setEncryption(
        userPassword.toByteArray(),
        ownerPassword.toByteArray(),
        PdfWriter.ALLOW_PRINTING,
        PdfWriter.ENCRYPTION_AES_128
    )
    pdfStamper.close()
    pdfReader.close()
}