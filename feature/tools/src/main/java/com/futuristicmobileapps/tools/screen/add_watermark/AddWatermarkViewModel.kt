package com.futuristicmobileapps.tools.screen.add_watermark

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futuristicmobileapps.itext.Status
import com.futuristicmobileapps.itext.ToolsRepository
import com.futuristicmobileapps.tools.util.file.getRealFilePathFromUri
import com.futuristicmobileapps.tools.util.file.uriToFileAsync
import com.futuristicmobileapps.tools.util.makeToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddWatermarkViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {


    // states
    var isLoading by mutableStateOf(true)

    var fileName by mutableStateOf("rotated_${System.currentTimeMillis()}")
    var watermarkText by mutableStateOf("Water Mark Text")

    var fileUri: Uri by mutableStateOf(Uri.EMPTY)
    var imageUri: Uri by mutableStateOf(Uri.EMPTY)
    var file by mutableStateOf(File(""))
    var totalPages by mutableIntStateOf(0)

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

    fun addWaterMarkImage(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            val filePath = getRealFilePathFromUri(context, imageUri)
            filePath?.let { filePath ->
                toolsRepository.addWatermarkImage(fileName, fileUri, filePath)
                    .collectLatest { result ->
                        when (result.status) {
                            Status.SUCCESS -> navToFiles()
                            Status.FAILURE -> context.makeToast(result.message)
                        }
                    }
            }

        }
    }

    fun addWaterMarkText(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            toolsRepository.addWatermarkText(fileName, fileUri, watermarkText)
                .collectLatest { result ->
                    when (result.status) {
                        Status.SUCCESS -> navToFiles()
                        Status.FAILURE -> context.makeToast(result.message)
                    }
                }
        }
    }

    fun compressPdf(context: Context,navToFiles: () -> Unit){
        viewModelScope.launch {
            toolsRepository.compressPdf(fileName,fileUri).collectLatest { result ->
                when(result.status){
                    Status.SUCCESS -> navToFiles()
                    Status.FAILURE -> context.makeToast(result.message)
                }
            }
        }
    }

    fun extractImages(context: Context,navToFiles: () -> Unit){
        viewModelScope.launch {
            toolsRepository.extractImages(fileName,fileUri).collectLatest { result ->
                when(result.status){
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