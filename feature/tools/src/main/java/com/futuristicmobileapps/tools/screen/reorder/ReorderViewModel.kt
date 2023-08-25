package com.futuristicmobileapps.tools.screen.reorder

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futuristicmobileapps.itext.Status
import com.futuristicmobileapps.itext.ToolsRepository
import com.futuristicmobileapps.tools.util.PdfPage
import com.futuristicmobileapps.tools.util.convertPdfToBitmapsAsync
import com.futuristicmobileapps.tools.util.file.uriToFileAsync
import com.futuristicmobileapps.tools.util.getRotationalAngle
import com.futuristicmobileapps.tools.util.makeToast
import com.futuristicmobileapps.tools.util.rotateBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReorderViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {

    // states
    var isLoading by mutableStateOf(true)
    var initScreen by mutableStateOf(true)


    var fileName by mutableStateOf("rotated_${System.currentTimeMillis()}")

    var fileUri: Uri by mutableStateOf(Uri.EMPTY)
    var file by mutableStateOf(File(""))
    var totalPages by mutableStateOf(0)

    var pdfPagesList = mutableStateListOf<PdfPage>()

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
                pdfPagesList.clear()
                pdfPagesList.addAll(context.convertPdfToBitmapsAsync(fileUri))
            }.onJoin

            async {
                getTotalPages(context)
            }.onJoin

        }
    }

    fun removeItem(index: Int) {
        pdfPagesList.removeAt(index)
    }

    fun moveItemUp(index: Int) {
        if (index > 0) {
            pdfPagesList.swapItems(index, index - 1)
        }
    }

    fun moveItemDown(index: Int) {
        if (index < pdfPagesList.size - 1) {
            pdfPagesList.swapItems(index, index + 1)
        }
    }

    fun removeOrReorder(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            val pageOrder = pdfPagesList.map { it.pageNumber }.toIntArray()
            toolsRepository.removeOrReOrder(fileName, fileUri, pageOrder).collectLatest {
                when (it.status) {
                    Status.SUCCESS -> navToFiles()
                    Status.FAILURE -> context.makeToast(it.message)
                }
            }
        }
    }

    fun updateRotation(index: Int, angle: Int) {
        viewModelScope.launch {
            val rotatedPdf = pdfPagesList[index].bitmap.rotateBitmap(angle)
            val rotateAngle = pdfPagesList[index].rotateAngle
            pdfPagesList[index] =
                pdfPagesList[index].copy(
                    bitmap = rotatedPdf,
                    rotateAngle = rotateAngle.getRotationalAngle(angle)
                )
        }
    }


    fun rotatePdf(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            val anglesList = pdfPagesList.map { it.rotateAngle }
            toolsRepository.rotatePdf(fileName, fileUri, anglesList).collectLatest {
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

fun MutableList<PdfPage>.swapItems(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}