package com.futuristicmobileapps.tools.screen.page_number

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futuristicmobileapps.itext.Status
import com.futuristicmobileapps.itext.ToolsRepository
import com.futuristicmobileapps.tools.util.file.uriToFileAsync
import com.futuristicmobileapps.tools.util.makeToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddPageNumberViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {


    // states
    var isLoading by mutableStateOf(true)

    var fileName by mutableStateOf("rotated_${System.currentTimeMillis()}")

    var fileUri: Uri by mutableStateOf(Uri.EMPTY)
    var file by mutableStateOf(File(""))
    var totalPages by mutableStateOf(0)

    fun updateUri(uri: Uri) {
        fileUri = uri
    }

    fun initPageNumberScreen(context: Context) {

        viewModelScope.launch {

            isLoading = true

            async {
                val localFile = fileUri.uriToFileAsync(context)
                localFile?.apply { file = localFile }
            }.onJoin


            async {
                getTotalPages(context)
            }.onJoin

        }
    }

    fun addPageNumber(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            toolsRepository.addPageNumber(fileName, fileUri).collectLatest { result ->
                when (result.status) {
                    Status.SUCCESS -> navToFiles()
                    Status.FAILURE -> context.makeToast(result.message)
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