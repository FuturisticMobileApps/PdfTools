package com.futuristicmobileapps.tools.screen.split

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.ActionButton
import com.futuristicmobileapps.designsystem.component.AddRangeButton
import com.futuristicmobileapps.designsystem.component.CircleButton
import com.futuristicmobileapps.designsystem.component.CircularProgressBar
import com.futuristicmobileapps.designsystem.component.DetailScreenCard
import com.futuristicmobileapps.designsystem.component.FileDetailsCard
import com.futuristicmobileapps.designsystem.component.SplitRangeCard
import com.futuristicmobileapps.designsystem.component.SubTitle
import com.futuristicmobileapps.designsystem.component.ToolDetailTopBar
import com.futuristicmobileapps.designsystem.util.SplitRange
import com.futuristicmobileapps.tools.util.pickPdfLauncher
import com.futuristicmobileapps.tools.util.singlePdfLauncherIntent


@Composable
fun SplitScreen(
    uri: Uri,
    navHostController: NavHostController,
    splitScreenViewModel: SplitScreenViewModel = hiltViewModel()
) {

    val context: Context = LocalContext.current

    val pickPdfLauncher = pickPdfLauncher { pdfFile ->
        pdfFile.uri?.let {
            with(splitScreenViewModel) {
                updateUri(it)
                intiSplitScreen(context)
            }
        }
    }


    with(splitScreenViewModel) {

        LaunchedEffect(key1 = uri) {
            updateUri(uri)
            intiSplitScreen(context)
        }

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {

            ToolDetailTopBar(title = R.string.split_pdf) {
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
                            name = fileName.value,
                            size = fileSize.value,
                            totalPages = totalPages.value.toString(),
                            modifier = Modifier.padding(top = 8.dp),
                            isLoading = isLoading.value
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        SplitRangeListUI(splitScreenViewModel)

                        if (isLoading.value)
                            CircularProgressBar()

                    }


                    ActionButton(text = R.string.split_pdf, modifier = Modifier.padding(16.dp)) {

                        splitRangeList.forEach { splitRange ->
                            splitPdf(
                                splitRange.fileName,
                                fileUri.value,
                                splitRange.from,
                                splitRange.to,
                            )
                        }

                    }

                }

            }

        }
    }

}


@Composable
fun ColumnScope.SplitRangeListUI(splitScreenViewModel: SplitScreenViewModel) {


    with(splitScreenViewModel) {

        LazyColumn(modifier = Modifier.weight(1f)) {

            item {
                SubTitle(subTitle = "Split Ranges :")
            }

            itemsIndexed(splitRangeList) { index: Int, item: SplitRange ->

                SplitRangeCard(
                    SplitRange(
                        fileName = item.fileName,
                        from = item.from,
                        to = item.to,
                        range = index + 1,
                    ),
                    totalPages = totalPages.value,
                    isDeleteVisible = index != 0 && index == splitRangeList.lastIndex,
                    onDeleteClicked = { deleteSplitRange(index) }
                ) { splitRange ->
                    updateSplitRanges(index, splitRange)
                }
            }


            item {

                AddRangeButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(vertical = 12.dp)
                ) {

                    val newSplitRange = SplitRange(
                        "${fileName.value}_split(${splitRangeList.size + 1})",
                        1,
                        totalPages.value,
                        splitRangeList.size + 1
                    )

                    addSplitRange(newSplitRange)
                }

            }
        }

    }

}

@Preview
@Composable
fun PreviewSplitScreen() {

}