package com.futuristicmobileapps.tools.screen.secure

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
class SecurePdfViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {

    var isLoading by mutableStateOf(true)
    var fileName by mutableStateOf("reordered")
    var password by mutableStateOf("mypdf")
    var fileUri: Uri by mutableStateOf(Uri.EMPTY)
    var file by mutableStateOf(File(""))
    var totalPages by mutableStateOf(0)

    fun updateUri(uri: Uri) {
        fileUri = uri
    }

    suspend fun initPagesScreen(context: Context) {

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

    suspend fun addPassword(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            toolsRepository.addPassword(fileName, password, fileUri).collectLatest { result ->
                when (result.status) {
                    Status.SUCCESS -> navToFiles()
                    Status.FAILURE -> context.makeToast(result.message)
                }
            }
        }
    }

    suspend fun removePassword(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            toolsRepository.removePassword(fileName, fileUri, password).collectLatest { result ->
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