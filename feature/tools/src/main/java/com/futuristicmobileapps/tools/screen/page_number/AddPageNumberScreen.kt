package com.futuristicmobileapps.tools.screen.page_number

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.futuristicmobileapps.designsystem.component.CircleButton
import com.futuristicmobileapps.designsystem.component.CircularProgressBar
import com.futuristicmobileapps.designsystem.component.DetailScreenCard
import com.futuristicmobileapps.designsystem.component.FileDetailsCard
import com.futuristicmobileapps.designsystem.component.FileNameTextField
import com.futuristicmobileapps.designsystem.component.SubTitle
import com.futuristicmobileapps.designsystem.component.ToolDetailTopBar
import com.futuristicmobileapps.tools.util.pickPdfLauncher
import com.futuristicmobileapps.tools.util.singlePdfLauncherIntent

@Composable
fun AddPageNumberScreen(
    uri: Uri,
    navHostController: NavHostController,
    pageNumberViewModel: AddPageNumberViewModel = hiltViewModel()
) {

    val context: Context = LocalContext.current

    val pickPdfLauncher = pickPdfLauncher { pdfFile ->
        pdfFile.uri?.let {
            with(pageNumberViewModel) {
                updateUri(it)
                initPageNumberScreen(context)
            }
        }
    }

    with(pageNumberViewModel) {

        LaunchedEffect(key1 = uri) {
            updateUri(uri)
            initPageNumberScreen(context)
        }

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {

            ToolDetailTopBar(title = R.string.add_page_number) {
                navHostController.popBackStack()
            }

            DetailScreenCard(modifier = Modifier.padding(top = 10.dp)) {

                Column(modifier = Modifier.fillMaxSize()) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(20.dp)
                    ) {


                        Row(modifier = Modifier.fillMaxWidth()) {
                            SubTitle(subTitle = "File Details :", modifier = Modifier.weight(1f))
                            CircleButton(icon = R.drawable.edit_icon) {
                                pickPdfLauncher.launch(singlePdfLauncherIntent)
                            }
                        }

                        FileDetailsCard(
                            name = file.name,
                            size = file.length().getSize(),
                            totalPages = totalPages.toString(),
                            modifier = Modifier.padding(top = 8.dp),
                            isLoading = isLoading
                        )

                        FileNameTextField(
                            value = fileName,
                            onValueChange = { fileName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {

                        }


                        if (isLoading)
                            CircularProgressBar()

                    }


                    ActionButton(
                        text = R.string.add_page_number,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        addPageNumber(context) {

                            navHostController.navigate("files") {
                                popUpTo("tools_graph") {
                                    inclusive = true
                                }
                            }
                        }

                    }

                }

            }
        }
    }
}