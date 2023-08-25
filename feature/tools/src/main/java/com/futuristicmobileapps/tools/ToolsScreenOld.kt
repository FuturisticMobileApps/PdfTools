package com.futuristicmobileapps.tools

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.RotatePageCard
import com.futuristicmobileapps.designsystem.component.ToolsCard
import com.futuristicmobileapps.tools.util.Action
import com.futuristicmobileapps.tools.util.convertPdfToBitmaps


@Composable
fun ToolsScreenTest() {

//    val toolsViewModel: ToolsViewModel = hiltViewModel()
//
//
//    val context = LocalContext.current
//
//    var singlePdfAction by remember { mutableStateOf(Action.SPLIT) }
//
//    var uriLocal: Uri? = null
//
//
//    val pdfLauncherIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//        addCategory(Intent.CATEGORY_OPENABLE)
//        type = "application/pdf"
//    }
//
//    val multiplePdfIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
//        type = "*/*"
//        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//    }
//
//
//    val pdfLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//
//            result.data?.data?.let { uri ->
//                when (singlePdfAction) {
//
////                    Action.SPLIT -> toolsViewModel.splitPdf("new_split", uri, 1, 4)
//
//                    Action.SPLIT -> {
//
//                    }
//
//                    Action.MERGE -> {
//                        val pageRanges = mutableListOf(
//                            Pair(2, 8),
//                            Pair(10, 20),
//                            Pair(30, 40),
//                            Pair(23, 46)
//                        )
//
//                        toolsViewModel.splitMultiplePdf(
//                            "${System.currentTimeMillis()}_splitted",
//                            uri,
//                            pageRanges
//                        )
//
//                    }
//
//                    Action.ROTATE -> {
////                        toolsViewModel.rotatePdf("rotated_pdf",uri)
//                    }
//
//                    Action.REORDER -> {
//                        uriLocal = uri
//                        toolsViewModel.updatePdfList(
//                            context.convertPdfToBitmaps(uri)
//                        )
//                    }
//
//                    Action.INVERT -> {
//                        toolsViewModel.invertPdf("inverted_file", uri)
//                    }
//
//                    Action.REMOVE_DUPLICATE -> {
////                        toolsViewModel.removeDuplicate("duplicate_removed",uri)
//                    }
//
//                    else -> {}
//                }
//            }
//        }
//
//
//    val pdfListLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//
//            result.data?.clipData?.let { clipData ->
//
//                val uriList = mutableListOf<Uri>()
//
//                if (clipData.itemCount == 1) {
//                    // TODO: Error message select more than one file
//                }
//
//                for (i in 0 until clipData.itemCount) {
//                    uriList.add(clipData.getItemAt(i).uri)
//                }
//
//                toolsViewModel.mergePdf("new_merged", uriList)
//
//                return@rememberLauncherForActivityResult
//            }
//
//        }
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = MaterialTheme.colorScheme.background)
//    ) {
//
//        Row {
//
//            ToolsCard(
//                modifier = Modifier,
//                imageDrawable = R.drawable.ic_merge,
//                toolText = R.string.merge_pdf,
//            ) {
//                pdfListLauncher.launch(multiplePdfIntent)
//            }
//
//            ToolsCard(
//                modifier = Modifier,
//                imageDrawable = R.drawable.ic_merge,
//                toolText = R.string.split_pdf,
//            ) {
//                singlePdfAction = Action.SPLIT
//                pdfLauncher.launch(pdfLauncherIntent)
//            }
//
//            ToolsCard(
//                modifier = Modifier,
//                imageDrawable = R.drawable.ic_merge,
//                toolText = R.string.reorder_pages,
//            ) {
//                singlePdfAction = Action.REORDER
//                pdfLauncher.launch(pdfLauncherIntent)
//            }
//        }
//
//        Button(
//            onClick = {
//                toolsViewModel.rotatePdf(
//                    "rotated",
//                    uriLocal!!
//                )
//            }) {
//            Text(text = "Save")
//        }
//
//        LazyColumn {
//
//            /*  itemsIndexed(toolsViewModel.pdfPagesList) { index, pdfPage ->
//
//                  PageCard(bitmap = pdfPage.bitmap,
//
//                      onCancelClicked = {
//                          toolsViewModel.removeItem(index)
//                      }, onUpClicked = {
//                          toolsViewModel.moveItemUp(index)
//                      }, onDownClicked = {
//                          toolsViewModel.moveItemDown(index)
//                      })
//              }*/
//
//            itemsIndexed(toolsViewModel.pdfPagesList) { index, pdfPage ->
//
//                RotatePageCard(bitmap = pdfPage.bitmap,
//                    onLeftClicked = {
//                        toolsViewModel.updateRotation(index, -90)
//                    }, onRightClicked = {
//                        toolsViewModel.updateRotation(index, +90)
//                    })
//            }
//
//
//        }
//
//
//    }


}


