package com.futuristicmobileapps.tools.screen.merge

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futuristicmobileapps.itext.Status
import com.futuristicmobileapps.itext.ToolsRepository
import com.futuristicmobileapps.tools.util.file.validateFileName
import com.futuristicmobileapps.tools.util.makeToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MergeViewModel @Inject constructor(
    private val toolsRepository: ToolsRepository
) : ViewModel() {

    var fileName by mutableStateOf("merge_${System.currentTimeMillis()}")

    val _uriList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
    val uriList = _uriList.asStateFlow()

    fun swapSections(from: Int, to: Int) {
        val fromItem = _uriList.value[from]
        val toItem = _uriList.value[to]
        val newList = _uriList.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem
        _uriList.value = newList
    }

    fun mergePdf(context: Context, navToFiles: () -> Unit) {
        viewModelScope.launch {
            toolsRepository.mergePdf(fileName.validateFileName(), uriList.value)
                .collectLatest { result ->
                    when (result.status) {
                        Status.SUCCESS -> navToFiles()
                        Status.FAILURE -> context.makeToast(result.message)
                    }
                }
        }
    }

}