package com.futuristicmobileapps.tools.screen.merge

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.futuristicmobileapps.apputil.getSize
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.ActionButton
import com.futuristicmobileapps.designsystem.component.FileNameTextField
import com.futuristicmobileapps.designsystem.component.PdfCard
import com.futuristicmobileapps.designsystem.component.ToolDetailTopBar
import com.futuristicmobileapps.designsystem.component.drag_drop_list.DragDropColumn
import com.futuristicmobileapps.designsystem.util.isValidString
import com.futuristicmobileapps.designsystem.util.validateString
import com.futuristicmobileapps.tools.navigation.ToolsGraph
import com.futuristicmobileapps.tools.navigation.ToolsScreens
import com.futuristicmobileapps.tools.util.file.uriToFile
import com.futuristicmobileapps.tools.util.makeToast
import com.futuristicmobileapps.tools.util.multiplePdfLauncherIntent
import com.futuristicmobileapps.tools.util.pickMultiplePdfsLauncher


@Composable
fun MergeScreen(
    navHostController: NavHostController,
    mergeViewModel: MergeViewModel = hiltViewModel(),
) {

    val context: Context = LocalContext.current

    var launchPicker by rememberSaveable { mutableStateOf(true) }


    val pickMultiplePdfLauncher = pickMultiplePdfsLauncher { pdfFile ->
        with(pdfFile) {
            if (errorMessage.isValidString()) {
                context.makeToast(errorMessage.validateString())
                return@pickMultiplePdfsLauncher
            }
            uriList?.let {
                mergeViewModel._uriList.value = it
            }
        }
    }

    if (launchPicker) {
        LaunchedEffect(key1 = true) {
            pickMultiplePdfLauncher.launch(multiplePdfLauncherIntent)
            launchPicker = false
        }
    }


    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {

        ToolDetailTopBar(title = R.string.merge_pdf) {
            navHostController.popBackStack()
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(20.dp)
        ) {

            Text(modifier = Modifier.padding(bottom = 8.dp), text = "Long press to shuffle list")

            DragDropColumn(
                items = mergeViewModel.uriList.collectAsState().value,
                onSwap = mergeViewModel::swapSections
            ) { uri ->

                val localFile = uri.uriToFile(context)!!

                localFile.let { file ->
                    PdfCard(
                        name = file.name,
                        size = "${file.length().getSize()} MB",
                        modifier = Modifier.height(100.dp)
                    )
                }

            }

        }

        FileNameTextField(
            value = mergeViewModel.fileName,
            onValueChange = { mergeViewModel.fileName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

        }

        ActionButton(text = R.string.merge_pdf, modifier = Modifier.padding(16.dp)) {

            mergeViewModel.mergePdf(context) {
                navHostController.navigate("files"){
                    popUpTo(ToolsScreens.Tools.route)
                    launchSingleTop = true
                }
            }
        }

    }

}







