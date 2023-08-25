package com.futuristicmobileapps.tools.screen.images_to_pdf

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.ActionButton
import com.futuristicmobileapps.designsystem.component.CircleButton
import com.futuristicmobileapps.designsystem.component.DetailScreenCard
import com.futuristicmobileapps.designsystem.component.FileNameTextField
import com.futuristicmobileapps.designsystem.component.SubTitle
import com.futuristicmobileapps.designsystem.component.ToolDetailTopBar


@Composable
fun ImagesToPdfScreen(
    navHostController: NavHostController,
    imagesToPdfViewModel: ImagesToPdfViewModel = hiltViewModel(),
) {

    val context: Context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { urisList ->
            (urisList.isNotEmpty()).let {
                imagesToPdfViewModel.imagesList.addAll(urisList)
            }
        }
    )

    with(imagesToPdfViewModel) {

        Column(
            modifier = Modifier
                .background(color = androidx.compose.material3.MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {

            ToolDetailTopBar(title = R.string.images_to_pdf) {
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


                        SubTitle(
                            subTitle = "File Details :",
                            modifier = Modifier.weight(1f)
                        )




                        FileNameTextField(
                            value = fileName,
                            onValueChange = { fileName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {

                        }

                        CircleButton(icon = R.drawable.camera_selected) {
                            pickImageLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }


                    }


                    ActionButton(
                        text = R.string.images_to_pdf,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        imagesToPdf(context) {
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

