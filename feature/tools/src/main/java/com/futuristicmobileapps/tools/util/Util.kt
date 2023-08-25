package com.futuristicmobileapps.tools.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job

fun Dp.isPortrait(): Boolean = this < 450.dp


inline fun CoroutineScope.isCompleteAllJobs(
    crossinline function: (Unit) -> Unit,
) {
    this.coroutineContext.job.invokeOnCompletion {

        function(Unit)

    }
}

