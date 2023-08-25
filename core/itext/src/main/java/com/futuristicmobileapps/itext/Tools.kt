package com.futuristicmobileapps.itext

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface Tools {

    suspend fun getNumberOfPages(uri: Uri): Result

    suspend fun splitPdf(fileName: String, uri: Uri, startPage: Int, endPage: Int): Flow<Result>

    suspend fun splitMultiplePdfs(
        fileName: String,
        uri: Uri,
        pageRanges: MutableList<Pair<Int, Int>>
    ): Flow<Result>

    suspend fun mergePdf(fileName: String, uriList: MutableList<Uri>): Flow<Result>

    suspend fun removeOrReOrder(fileName: String, uri: Uri, pageOrder: IntArray): Flow<Result>

    suspend fun invertPdf(fileName: String, uri: Uri): Flow<Result>

    suspend fun rotatePdf(fileName: String, uri: Uri, anglesList: List<Int>): Flow<Result>

    suspend fun addPageNumber(fileName: String, uri: Uri): Flow<Result>

    suspend fun addPassword(fileName: String, password: String, uri: Uri): Flow<Result>

    suspend fun removePassword(fileName: String, uri: Uri, password: String): Flow<Result>

    suspend fun addWatermarkImage(fileName: String, fileUri: Uri, filePath: String): Flow<Result>

    suspend fun addWatermarkText(fileName: String, fileUri: Uri, watermarkText: String): Flow<Result>

    suspend fun compressPdf(fileName: String, fileUri: Uri, compressionLevel : Int = 9): Flow<Result>

    suspend fun extractImages(folderName : String, fileUri: Uri): Flow<Result>

    suspend fun docToPdf(fileName : String, fileUri: Uri): Flow<Result>

    suspend fun imagesToPdf(fileName: String,imagePaths: List<String>) : Flow<Result>

}