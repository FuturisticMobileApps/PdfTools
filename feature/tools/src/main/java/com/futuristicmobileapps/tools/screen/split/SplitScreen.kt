package com.futuristicmobileapps.tools.screen.split

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.ActionButton
import com.futuristicmobileapps.designsystem.component.AddRangeButton
import com.futuristicmobileapps.designsystem.component.CircleButton
import com.futuristicmobileapps.designsystem.component.CircleIcon
import com.futuristicmobileapps.designsystem.component.CircularProgressBar
import com.futuristicmobileapps.designsystem.component.DetailScreenCard
import com.futuristicmobileapps.designsystem.component.FileDetailsCard
import com.futuristicmobileapps.designsystem.component.PageHeader
import com.futuristicmobileapps.designsystem.component.PageSubHeader
import com.futuristicmobileapps.designsystem.component.SectionDivider
import com.futuristicmobileapps.designsystem.component.SplitRangeCard
import com.futuristicmobileapps.designsystem.component.SubTitle
import com.futuristicmobileapps.designsystem.theme.cardBackground
import com.futuristicmobileapps.designsystem.theme.subTextColor
import com.futuristicmobileapps.designsystem.theme.textColor
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
                .fillMaxSize()
        ) {

          /*  PageHeader(title = R.string.split_pdf, color = MaterialTheme.colorScheme.textColor) {
                navHostController.popBackStack()
            }

            SectionFileDetails()*/


                     FileDetailsCard(
                         name = fileName.value,
                         size = fileSize.value,
                         date = "Jan 31. 12.30 PM",
                         totalPages = totalPages.value.toString(),
                         modifier = Modifier.padding(horizontal = 12.dp),
                         isLoading = isLoading.value
                     )

                     SectionDivider(top = 24.dp)

                     PageSubHeader(
                         name = R.string.split_options,
                         modifier = Modifier.padding(start = 12.dp, top = 20.dp)
                     )


                     Row(modifier = Modifier
                         .fillMaxWidth()
                         .padding(12.dp)
                         .clickable { }
                         .background(color = MaterialTheme.colorScheme.background)) {

                         Column(Modifier.weight(1f)) {

                             Text(
                                 text = stringResource(id = R.string.custom_split_range),
                                 style = MaterialTheme.typography.bodyLarge,
                                 color = MaterialTheme.colorScheme.textColor
                             )
                             Text(
                                 text = stringResource(id = R.string.custom_range_sub_text),
                                 style = MaterialTheme.typography.labelMedium,
                                 color = MaterialTheme.colorScheme.subTextColor
                             )
                         }

                         Image(
                             modifier = Modifier
                                 .size(16.dp)
                                 .align(Alignment.CenterVertically),
                             painter = painterResource(id = R.drawable.forward_icon),
                             colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.textColor),
                             contentDescription = stringResource(id = R.string.forward_icon)
                         )

                     }

                     Spacer(modifier = Modifier.height(10.dp))

                     Row {
                         RadioButton(selected = false, onClick = { })
                         Column {
                             Text(
                                 text = stringResource(id = R.string.fixed_range),
                                 style = MaterialTheme.typography.bodyLarge,
                                 color = MaterialTheme.colorScheme.textColor
                             )
                             Text(
                                 text = stringResource(id = R.string.fixed_range_sub_text),
                                 style = MaterialTheme.typography.labelMedium,
                                 color = MaterialTheme.colorScheme.subTextColor
                             )
                         }
                     }

                     Spacer(modifier = Modifier.height(10.dp))

                     Row {
                         RadioButton(selected = true, onClick = { })
                         Column {
                             Text(
                                 text = stringResource(id = R.string.separate_all_pages),
                                 style = MaterialTheme.typography.bodyLarge,
                                 color = MaterialTheme.colorScheme.textColor
                             )
                             Text(
                                 text = "Each page will be separated, that means ${totalPages.value} pdf files will be created",
                                 style = MaterialTheme.typography.labelMedium,
                                 color = MaterialTheme.colorScheme.subTextColor
                             )
                         }
                     }



            /*  SplitRangeCard(
                  SplitRange(
                      fileName = "item.fileName",
                      from = 2,
                      to = 0,
                      range = 3,
                  ),
                  totalPages = totalPages.value,
                  isDeleteVisible = true,
                  onDeleteClicked = {  }
              ) { splitRange ->

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

               }*/

        }


    }

}

@Composable
fun SectionFileDetails() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.cardBackground
        ),
    ) {

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Thisdflksdfsdfsd.pdf",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.textColor
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(text = "Jul 13,12 : 30 PM | 49.48 MB | 677 Pages",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.textColor)
            }

            Row {

                CircleIcon(icon = R.drawable.preview_icon, modifier = Modifier.padding(end = 6.dp))

                CircleIcon(icon = R.drawable.refresh_icon)

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