package com.futuristicmobileapps.tools.screen.add_watermark

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.futuristicmobileapps.apputil.getSize
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
fun AddWatermarkScreen(
    uri: Uri,
    navHostController: NavHostController,
    addWatermarkViewModel: AddWatermarkViewModel = hiltViewModel(),
) {

    val context: Context = LocalContext.current

    val pickPdfLauncher = pickPdfLauncher { pdfFile ->
        pdfFile.uri?.let {
            with(addWatermarkViewModel) {
                updateUri(it)
                initPageNumberScreen(context)
            }
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                addWatermarkViewModel.imageUri = uri
            }
        }
    )

    with(addWatermarkViewModel) {

        LaunchedEffect(key1 = uri) {
            updateUri(uri)
            initPageNumberScreen(context)
        }

        Column(
            modifier = Modifier
                .background(color = androidx.compose.material3.MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {

            ToolDetailTopBar(title = com.futuristicmobileapps.designsystem.R.string.add_watermark) {
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
                            SubTitle(
                                subTitle = "File Details :",
                                modifier = Modifier.weight(1f)
                            )
                            CircleButton(icon = com.futuristicmobileapps.designsystem.R.drawable.edit_icon) {
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

                        CircleButton(icon = com.futuristicmobileapps.designsystem.R.drawable.camera_selected) {
                          pickImageLauncher.launch(PickVisualMediaRequest(
                              ActivityResultContracts.PickVisualMedia.ImageOnly
                          ))
                        }

                        SelectedImage(imageUri = imageUri)


                        if (isLoading)
                            CircularProgressBar()

                    }


                    ActionButton(
                        text = com.futuristicmobileapps.designsystem.R.string.add_watermark,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        extractImages(context) {
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


@Composable
fun SelectedImage(imageUri: Uri) {

    AsyncImage(
        modifier = Modifier.size(200.dp),
        model = imageUri,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

}


