package com.futuristicmobileapps.tools.screen.images_to_pdf

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
import com.futuristicmobileapps.tools.util.file.getRealFilePathFromUri
import com.futuristicmobileapps.tools.util.makeToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesToPdfViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {

    var fileName by mutableStateOf("imagesToPdf")

    var imagesList = mutableStateListOf<Uri>()

    fun imagesToPdf(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            val uriStringList = imagesList.mapNotNull { getRealFilePathFromUri(context, it) }
            toolsRepository.imagesToPdf(fileName, uriStringList).collectLatest { result ->
                when (result.status) {
                    Status.SUCCESS -> navToFiles()
                    Status.FAILURE -> context.makeToast(result.message)
                }
            }
        }
    }

}