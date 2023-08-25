package com.futuristicmobileapps.tools.screen.split

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futuristicmobileapps.apputil.getSize
import com.futuristicmobileapps.designsystem.util.SplitRange
import com.futuristicmobileapps.designsystem.util.isValidInt
import com.futuristicmobileapps.designsystem.util.isValidLong
import com.futuristicmobileapps.itext.Status
import com.futuristicmobileapps.itext.ToolsRepository
import com.futuristicmobileapps.tools.util.file.decodeFileAsync
import com.futuristicmobileapps.tools.util.file.uriToFile
import com.futuristicmobileapps.tools.util.makeToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplitScreenViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository,
) : ViewModel() {

    // states
    var isLoading = mutableStateOf(true)


    // values
    var totalPages = mutableStateOf(0)
    var fileName = mutableStateOf("unknown")
    var fileSize = mutableStateOf("-")
    var fileUri = mutableStateOf(Uri.EMPTY)
    private val _splitRangeList = mutableStateListOf<SplitRange>()
    val splitRangeList: List<SplitRange> = _splitRangeList


    fun intiSplitScreen(context: Context) {

        viewModelScope.launch {

            isLoading.value = true

            val file = fileUri.value.uriToFile(context)

            file?.apply {
                fileName.value = name ?: fileUri.toString()
                fileSize.value = if (length().isValidLong()) "${length().getSize()} MB" else "-"
            }
            getTotalPages(fileUri.value, context)
        }
    }



    fun updateUri(uri: Uri) {
        fileUri.value = uri
    }

    fun addSplitRange(splitRange: SplitRange) {
        viewModelScope.launch {
            _splitRangeList.add(splitRange)
        }
    }

    fun deleteSplitRange(index: Int) {
        viewModelScope.launch {
            _splitRangeList.removeAt(index)
        }
    }

    fun updateSplitRanges(index: Int, splitRange: SplitRange) {
        viewModelScope.launch {
            _splitRangeList[index] = splitRange
        }
    }

    fun splitPdf(fileName: String, uri: Uri, startPage: Int, endPage: Int) {

        val fileDirectory =  Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DOCUMENTS}/pdfFiles")

        if (fileDirectory == null || !fileDirectory.exists()){
            fileDirectory.mkdirs()
        }

        isLoading.value = true
        viewModelScope.launch {
            toolsRepository.splitPdf(fileName, uri, startPage, endPage).collectLatest {
                isLoading.value = false
                Log.i("TAG", "splitPdf: ${it.message}")
            }

        }
    }

    private suspend fun getTotalPages(uri: Uri, context: Context) {

        val result = toolsRepository.getNumberOfPages(uri)
        isLoading.value = false

        when (result.status) {
            Status.SUCCESS -> {
                totalPages.value = result.numberOfPages
                addFirstItem(fileName.value, totalPages.value)
            }
            Status.FAILURE -> context.makeToast(result.message)
        }
    }

    private fun addFirstItem(fileName: String, totalPages: Int) {

        val splitRange = SplitRange(
            fileName + "_split(${splitRangeList.size + 1})",
            1,
            totalPages,
            splitRangeList.size + 1
        )
        if (splitRangeList.isEmpty())
         addSplitRange(splitRange)
    }


}