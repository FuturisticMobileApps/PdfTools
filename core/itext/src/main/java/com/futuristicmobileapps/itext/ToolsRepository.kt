package com.futuristicmobileapps.itext

import android.content.Context
import android.net.Uri
import com.futuristicmobileapps.itext.util.addPageNumber
import com.futuristicmobileapps.itext.util.addPassword
import com.futuristicmobileapps.itext.util.addWatermarkImage
import com.futuristicmobileapps.itext.util.addWatermarkText
import com.futuristicmobileapps.itext.util.compressPdf
import com.futuristicmobileapps.itext.util.docToPdf
import com.futuristicmobileapps.itext.util.extractImages
import com.futuristicmobileapps.itext.util.getNumberOfPagesAsync
import com.futuristicmobileapps.itext.util.imagesToPdfImpl
import com.futuristicmobileapps.itext.util.invertPdf
import com.futuristicmobileapps.itext.util.mergePdfItext
import com.futuristicmobileapps.itext.util.removeOrReorderPages
import com.futuristicmobileapps.itext.util.removePassword
import com.futuristicmobileapps.itext.util.rotatePdf
import com.futuristicmobileapps.itext.util.splitMultiplePdfs
import com.futuristicmobileapps.itext.util.splitPdfItext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToolsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : Tools {

    override suspend fun getNumberOfPages(uri: Uri): Result = context.getNumberOfPagesAsync(uri)

    override suspend fun splitPdf(
        fileName: String,
        uri: Uri,
        startPage: Int,
        endPage: Int
    ): Flow<Result> =
        context.splitPdfItext(fileName, uri, startPage, endPage)

    override suspend fun splitMultiplePdfs(
        fileName: String,
        uri: Uri,
        pageRanges: MutableList<Pair<Int, Int>>
    ): Flow<Result> = context.splitMultiplePdfs(fileName, uri, pageRanges)


    override suspend fun mergePdf(fileName: String, uriList: MutableList<Uri>): Flow<Result> =
        context.mergePdfItext(fileName, uriList)


    override suspend fun rotatePdf(
        fileName: String,
        uri: Uri,
        anglesList: List<Int>
    ): Flow<Result> =
        context.rotatePdf(fileName, uri, anglesList)


    override suspend fun removeOrReOrder(
        fileName: String,
        uri: Uri,
        pageOrder: IntArray
    ): Flow<Result> =
        context.removeOrReorderPages(fileName, uri, pageOrder)

    override suspend fun invertPdf(fileName: String, uri: Uri): Flow<Result> =
        context.invertPdf(fileName, uri)

    override suspend fun addPageNumber(fileName: String, uri: Uri): Flow<Result> =
        context.addPageNumber(fileName, uri)

    override suspend fun addPassword(fileName: String, password: String, uri: Uri): Flow<Result> =
        context.addPassword(fileName, password, uri)

    override suspend fun removePassword(
        fileName: String,
        uri: Uri,
        password: String
    ): Flow<Result> =
        context.removePassword(fileName, uri, password)

    override suspend fun addWatermarkImage(
        fileName: String,
        fileUri: Uri,
        filePath: String
    ): Flow<Result> =
        context.addWatermarkImage(fileName, fileUri, filePath)

    override suspend fun addWatermarkText(
        fileName: String,
        fileUri: Uri,
        watermarkText: String
    ): Flow<Result> =
        context.addWatermarkText(fileName, fileUri, watermarkText)

    override suspend fun compressPdf(
        fileName: String,
        fileUri: Uri,
        compressionLevel: Int
    ): Flow<Result> = context.compressPdf(fileName, fileUri, compressionLevel)

    override suspend fun extractImages(folderName: String, fileUri: Uri): Flow<Result> =
        context.extractImages(folderName, fileUri)

    override suspend fun docToPdf(fileName: String, fileUri: Uri): Flow<Result> =
        context.docToPdf(fileName,fileUri)

    override suspend fun imagesToPdf(
        fileName: String,
        imagePaths: List<String>,
    ): Flow<Result> = imagesToPdfImpl(fileName, imagePaths)
}