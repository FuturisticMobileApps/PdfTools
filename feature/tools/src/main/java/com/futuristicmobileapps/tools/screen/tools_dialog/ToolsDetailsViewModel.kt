package com.futuristicmobileapps.tools.screen.tools_dialog

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futuristicmobileapps.itext.Status
import com.futuristicmobileapps.itext.ToolsRepository
import com.futuristicmobileapps.tools.util.PdfPage
import com.futuristicmobileapps.tools.util.convertPdfToBitmapsAsync
import com.futuristicmobileapps.tools.util.file.uriToFileAsync
import com.futuristicmobileapps.tools.util.getSortedList
import com.futuristicmobileapps.tools.util.makeToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ToolsDetailsViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {

    // states
    var isLoading by mutableStateOf(true)
    var initScreen by mutableStateOf(true)
    var fileName by mutableStateOf("reordered")
    var isInvert by mutableStateOf(false)
    var isSingleButton by mutableStateOf(false)


    var contentText by mutableStateOf("")

    var fileUri: Uri by mutableStateOf(Uri.EMPTY)
    var file by mutableStateOf(File(""))
    var totalPages by mutableStateOf(0)

    var pdfPagesList = mutableStateListOf<PdfPage>()

    var sortedPages by mutableStateOf(intArrayOf())

    fun updateUri(uri: Uri) {
        fileUri = uri
    }

    fun initPagesScreen(context: Context) {

        viewModelScope.launch {

            isLoading = true

            async {
                val localFile = fileUri.uriToFileAsync(context)
                localFile?.apply { file = localFile }
            }.onJoin

            async {
                if (!isInvert) {
                    pdfPagesList = context.convertPdfToBitmapsAsync(fileUri).toMutableStateList()
                }
            }.onJoin


            async {
                getTotalPages(context)
            }.onJoin

        }
    }

    fun addPagesIntoPdf() {
        viewModelScope.launch {
            sortedPages = pdfPagesList.getSortedList().map { it.pageNumber + 1 }.toIntArray()
            val difference = pdfPagesList.size - sortedPages.size
            contentText = if (difference > 0) {
                isSingleButton = true
                "Found $difference duplicate pages, Do you want to remove all $difference pages from PDF"
            } else {
                "No duplicate pages found in this pdf"
            }
        }
    }


    fun invertPdf(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            toolsRepository.invertPdf(fileName, fileUri).collectLatest {
                when (it.status) {
                    Status.SUCCESS -> navToFiles()
                    Status.FAILURE -> context.makeToast(it.message)
                }
            }

        }
    }

    fun removeDuplicate(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            val sortedPages = pdfPagesList.getSortedList().map { it.pageNumber + 1 }.toIntArray()
            toolsRepository.removeOrReOrder(fileName, fileUri, sortedPages).collectLatest {
                when (it.status) {
                    Status.SUCCESS -> navToFiles()
                    Status.FAILURE -> context.makeToast(it.message)
                }
            }
        }
    }

    private suspend fun getTotalPages(context: Context) {
        val result = toolsRepository.getNumberOfPages(fileUri)
        isLoading = false
        when (result.status) {
            Status.SUCCESS -> totalPages = result.numberOfPages
            Status.FAILURE -> context.makeToast(result.message)
        }
    }
}