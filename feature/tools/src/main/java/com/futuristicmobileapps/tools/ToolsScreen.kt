package com.futuristicmobileapps.tools

import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.AppHeader
import com.futuristicmobileapps.designsystem.component.PageSubHeader
import com.futuristicmobileapps.designsystem.theme.PdfToolsIcons
import com.futuristicmobileapps.designsystem.theme.Size
import com.futuristicmobileapps.designsystem.util.isValidString
import com.futuristicmobileapps.designsystem.util.validateString
import com.futuristicmobileapps.tools.navigation.ToolsScreens
import com.futuristicmobileapps.tools.screen.tools_dialog.ToolsDialog
import com.futuristicmobileapps.tools.util.Action
import com.futuristicmobileapps.tools.util.isPortrait
import com.futuristicmobileapps.tools.util.makeToast
import com.futuristicmobileapps.tools.util.pickPdfLauncher
import com.futuristicmobileapps.tools.util.singlePdfLauncherIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder


@Composable
fun ToolsScreen(
    navHostController: NavHostController,
    toolsViewModel: ToolsViewModel = hiltViewModel(),
) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val scope = rememberCoroutineScope()


    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = if (screenWidth.isPortrait()) (screenWidth - 24.dp) / 3 else 124.dp
    val cardHeight = if (screenWidth.isPortrait()) cardWidth + 10.dp else 134.dp

    val pdfToolsIcons = PdfToolsIcons(isSystemInDarkTheme())
    val basicToolsList = getBasicToolsList()
    val secureToolsList = getSecureToolsList()

    var pdfAction by remember { mutableStateOf(Action.NONE) }


    val pickPdfLauncher = pickPdfLauncher { pdfFile ->

        with(pdfFile) {

            if (pdfFile.errorMessage.isValidString()) {
                context.makeToast(errorMessage.validateString())
                return@pickPdfLauncher
            }

            val uri = Uri.parse(pdfFile.uri!!.toString())

            val encodedUri: String = with(Dispatchers.IO) {
                URLEncoder.encode(
                    uri.toString(),
                    "UTF-8"
                )
            }


            when (pdfAction) {

                Action.SPLIT -> {
                    scope.launch {
                        navHostController.navigate(route = ToolsScreens.Split.setUri(encodedUri))
                    }
                }

                Action.MERGE -> {
                    scope.launch {
                        navHostController.navigate(route = ToolsScreens.Merge.route)
                    }
                }

                Action.ROTATE -> {
                    scope.launch {
                        navHostController.navigate(route = ToolsScreens.Rotate.setUri(encodedUri))
                    }
                }

                Action.REORDER -> {
                    scope.launch {
                        navHostController.navigate(
                            route = ToolsScreens.RemoveOrReorder.setUri(
                                encodedUri
                            )
                        )
                    }
                }

                Action.INVERT -> {

                    with(toolsViewModel) {
                        scope.launch {
                            updateUri(uri)
                            isInvert = true
                            initPagesScreen(context)
                            contentText =
                                "Invert all pages from top to bottom, last page is first& first page is lost"
                            isShowDialog = true
                        }
                    }
                }

                Action.REMOVE_DUPLICATE -> {
                    with(toolsViewModel) {
                        scope.launch {
                            isInvert = false
                            updateUri(pdfFile.uri)
                            initPagesScreen(context)
                            isShowDialog = true
                        }
                    }
                }

                Action.ADD_PAGE_NUMBER -> {
                    scope.launch {
                        navHostController.navigate(
                            route = ToolsScreens.AddPageNumber.setUri(
                                encodedUri
                            )
                        )
                    }
                }

                Action.ADD_PASSWORD -> {
                    scope.launch {
                        navHostController.navigate(
                            route = ToolsScreens.SecurePdf.setUriAndAddPassword(
                                encodedUri,
                                true
                            )
                        )
                    }
                }

                Action.REMOVE_PASSWORD -> {
                    scope.launch {
                        navHostController.navigate(
                            route = ToolsScreens.SecurePdf.setUriAndAddPassword(
                                encodedUri,
                                false
                            )
                        )
                    }
                }

                Action.ADD_WATERMARK -> {
                    scope.launch {
                        navHostController.navigate(
                            route = ToolsScreens.AddWatermark.setUri(
                                encodedUri
                            )
                        )
                    }
                }

                Action.IMAGES_TO_PDF -> {
                    scope.launch {
                        navHostController.navigate(route = ToolsScreens.ImagesToPdf.route)
                    }
                }

                Action.DOC_TO_PDF -> {
                    scope.launch {
                        navHostController.navigate(
                            route = ToolsScreens.DocToPdf.setUri(
                                encodedUri
                            )
                        )
                    }
                }

                Action.REMOVE_PAGE -> {}

                Action.PDF_TO_IMAGES -> {}

                Action.NONE -> {}
            }

        }
    }

    // Tools UI
    Column(modifier = Modifier.fillMaxSize()) {

        AppHeader()

      /*  Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .shadow(elevation = 0.4.dp))
*/
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {

            PageSubHeader(
                name = R.string.modify_tools,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp)
            )

            ToolsAdaptiveLayout(
                screenWidth = screenWidth,
                toolsList = basicToolsList,
                modifier = Modifier
                    .size(cardWidth, cardHeight)
                    .padding(10.dp)
            ) { pdfTool ->

                pdfAction = pdfTool.action

                when (pdfAction) {
                    Action.MERGE -> navHostController.navigate(ToolsScreens.Merge.route)
                    Action.IMAGES_TO_PDF -> navHostController.navigate(ToolsScreens.ImagesToPdf.route)
                    else -> pickPdfLauncher.launch(singlePdfLauncherIntent)
                }
            }

            PageSubHeader(
                name = R.string.secure_tools,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp)
            )

            ToolsAdaptiveLayout(
                screenWidth = screenWidth,
                toolsList = secureToolsList,
                modifier = Modifier
                    .size(cardWidth, cardHeight)
                    .padding(10.dp)
            ) { pdfTool ->
                pdfAction = pdfTool.action
                pickPdfLauncher.launch(singlePdfLauncherIntent)
            }

        }


        Spacer(modifier = Modifier.height(Size.bottomBar))

    }

    with(toolsViewModel) {

        if (toolsViewModel.isShowDialog)
            ToolsDialog(
                contentText = contentText,
                isSingleButton = isSingleButton || isInvert,
                isLoading = isLoading,
                file = file,
                fileName = fileName,
                onFileName = {
                    fileName = it
                },
                onDismiss = {
                    toolsViewModel.isShowDialog = false
                }) {

                toolsViewModel.isShowDialog = false

                if (isInvert)
                    invertPdf(context) {
                        navHostController.navigate("files") {
                            popUpTo("tools_graph") {
                                inclusive = true
                            }
                        }
                    }
                else
                    removeDuplicate(context) {
                        navHostController.navigate("files") {
                            popUpTo("tools_graph") {
                                inclusive = true
                            }
                        }
                    }

            }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewToolsScreen() {

}
