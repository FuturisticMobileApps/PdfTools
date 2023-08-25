package com.futuristicmobileapps.tools

import com.futuristicmobileapps.itext.Result

sealed interface UiState {

    data class Data(val result: Result) : UiState

    object Loading : UiState

    data class Error(val message : String) : UiState
}

sealed interface ScreenState{

    object OK : ScreenState

    data class Error(val message: String) : ScreenState
}