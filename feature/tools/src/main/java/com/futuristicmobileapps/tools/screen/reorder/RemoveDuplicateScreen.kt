package com.futuristicmobileapps.tools.screen.reorder

import android.content.Context
import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun RemoveDuplicateScreen(
    uri: Uri,
    navHostController: NavHostController,
    reorderViewModel: ReorderViewModel = hiltViewModel(),
) {

    val context: Context = LocalContext.current

    with(reorderViewModel){

        AlertDialog(

            onDismissRequest = {  },
            confirmButton = { }
        )
    }
}
