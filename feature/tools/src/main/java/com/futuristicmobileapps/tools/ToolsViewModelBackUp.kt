package com.futuristicmobileapps.tools

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futuristicmobileapps.designsystem.util.SplitRange
import com.futuristicmobileapps.itext.Result
import com.futuristicmobileapps.itext.Status
import com.futuristicmobileapps.itext.ToolsRepository
import com.futuristicmobileapps.tools.util.PdfPage
import com.futuristicmobileapps.tools.util.file.decodeFile
import com.futuristicmobileapps.tools.util.getRotationalAngle
import com.futuristicmobileapps.tools.util.getSortedList
import com.futuristicmobileapps.tools.util.rotateBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToolsViewMfghodel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {

    var isShowDialog by mutableStateOf(false)

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    var pdfPagesList = mutableStateListOf<PdfPage>()

    var splitRangeList = mutableStateListOf<SplitRange>()

    fun getFileFromUri(context: Context, uri: Uri) = decodeFile(context, uri)

    fun addSplitRange(splitRange: SplitRange) {
        viewModelScope.launch {
            splitRangeList.add(splitRange)
        }
    }

    fun deleteSplitRange(index: Int) {
        viewModelScope.launch {
            splitRangeList.removeAt(index)
        }
    }

    fun updatePdfList(newPdfList: MutableList<PdfPage>) {
        viewModelScope.launch {
            pdfPagesList.clear()
            pdfPagesList.addAll(newPdfList)
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

    fun removeItem(index: Int) {
        pdfPagesList.removeAt(index)
    }

    fun moveItemUp(index: Int) {
        if (index > 0) {
            pdfPagesList.swapItefms(index, index - 1)
        }
    }

    fun moveItemDown(index: Int) {
        if (index < pdfPagesList.size - 1) {
            pdfPagesList.swapItefms(index, index + 1)
        }
    }


    fun mergePdf(fileName: String, uriList: MutableList<Uri>) {
        viewModelScope.launch {
            toolsRepository.mergePdf(fileName, uriList).collectLatest {
                Log.i("TAG", "mergePdf: ${it.message}")
            }
        }
    }

    fun splitPdf(fileName: String, uri: Uri, startPage: Int, endPage: Int) {
        viewModelScope.launch {
            toolsRepository.splitPdf(fileName, uri, startPage, endPage).collectLatest {
                Log.i("TAG", "splitPdf: ${it.message}")
            }

        }
    }

    fun splitMultiplePdf(fileName: String, uri: Uri, pageRanges: MutableList<Pair<Int, Int>>) {
        viewModelScope.launch {
            toolsRepository.splitMultiplePdfs(fileName, uri, pageRanges).collectLatest {
                Log.i("TAG", "splittedPdfs: ${it.message}")
            }
        }
    }

    fun invertPdf(fileName: String, uri: Uri) {
        viewModelScope.launch {
            toolsRepository.invertPdf(fileName, uri).collectLatest {
                Log.i("TAG", "invertedPdf: ${it.message}")
            }

        }
    }

    fun removeOrReorder(fileName: String, uri: Uri, pageOrder: IntArray) {
        viewModelScope.launch {
            toolsRepository.removeOrReOrder(fileName, uri, pageOrder).collectLatest {
                Log.i("TAG", "reOrderedPdf: ${it.message}")
            }
        }
    }

    fun removeDuplicate(fileName: String, uri: Uri) {
        viewModelScope.launch {
            val sortedPages = pdfPagesList.getSortedList().map { it.pageNumber + 1 }.toIntArray()
            toolsRepository.removeOrReOrder(fileName, uri, sortedPages).collectLatest {
                Log.i("TAG", "reOrderedPdf: ${it.message}")
            }
        }
    }

    fun rotatePdf(fileName: String, uri: Uri) {
        viewModelScope.launch {
            val anglesList = pdfPagesList.map { it.rotateAngle }
            toolsRepository.rotatePdf(fileName, uri, anglesList).collectLatest {
                Log.i("TAG", "rotatedPdf: ${it.message}")
            }
        }
    }


    fun getTotalPages(uri: Uri) {
       /* viewModelScope.launch {
            val result = toolsRepository.getNumberOfPages(uri)
            when (result.status) {
                Status.SUCCESS -> _uiState.update { UiState.Data(Result(numberOfPages = result.numberOfPages)) }
                Status.FAILURE -> _uiState.update { UiState.Error(message = result.message) }
            }
        }*/
    }
}

fun MutableList<PdfPage>.swapItefms(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}



