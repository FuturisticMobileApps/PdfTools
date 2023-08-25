package com.futuristicmobileapps.tools.screen.reorder

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.futuristicmobileapps.apputil.getSize
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.ActionButton
import com.futuristicmobileapps.designsystem.component.FileDetailsCard
import com.futuristicmobileapps.designsystem.component.FileNameTextField
import com.futuristicmobileapps.designsystem.component.RotatePageCard
import com.futuristicmobileapps.designsystem.component.ToolDetailTopBar
import com.futuristicmobileapps.designsystem.util.intToString


@Composable
fun RotateScreen(
    uri: Uri,
    navHostController: NavHostController,
    rotateViewModel: ReorderViewModel = hiltViewModel(),
) {

    val context: Context = LocalContext.current

    with(rotateViewModel) {

        if (initScreen) {
            LaunchedEffect(key1 = uri) {
                updateUri(uri)
                initPagesScreen(context)
                initScreen = false
            }
        }


        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {

            ToolDetailTopBar(title = R.string.rotate_pdf) {
                navHostController.popBackStack()
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {


                FileDetailsCard(
                    name = file.name ?: fileUri.toString(),
                    size = "${file.length().getSize()} MB",
                    totalPages = totalPages.intToString(),
                    modifier = Modifier,
                    isLoading = isLoading
                )


                LazyColumn {

                    itemsIndexed(pdfPagesList) { index, pdfPage ->

                        RotatePageCard(
                            bitmap = pdfPage.bitmap,
                            pageNumber = index + 1,
                            onLeftClicked = {
                                updateRotation(index, -90)
                            }, onRightClicked = {
                                updateRotation(index, +90)
                            })

                    }
                }

            }

            FileNameTextField(
                value = rotateViewModel.fileName,
                onValueChange = { rotateViewModel.fileName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

            }


            ActionButton(text = R.string.rotate_pdf, modifier = Modifier.padding(16.dp)) {
                rotateViewModel.rotatePdf(context) {
                    navHostController.navigate("files") {
                        popUpTo("tools_graph")
                        launchSingleTop = true
                    }
                }
            }

        }

    }

}

