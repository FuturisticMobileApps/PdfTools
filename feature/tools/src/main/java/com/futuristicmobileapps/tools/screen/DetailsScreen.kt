package com.futuristicmobileapps.tools.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.ActionButton
import com.futuristicmobileapps.designsystem.component.FileNameTextField
import com.futuristicmobileapps.designsystem.component.PdfCard
import com.futuristicmobileapps.designsystem.component.ToolDetailTopBar

@Composable
fun DetailsScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        var fileName by remember { mutableStateOf("") }

        ToolDetailTopBar(title = R.string.split_pdf) {}

        /*  Card(
              modifier = Modifier.padding(top = 10.dp),
              shape = RoundedCornerShape(topStartPercent = 12, topEndPercent = 12),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
              elevation = CardDefaults.cardElevation(
                  defaultElevation = 20.dp,
                  pressedElevation = 25.dp,
              ),
          ) {*/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {


            PdfCard(name = "This is the new updated padf dlfs.pdf", size = "123.MB")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 8.dp)
            ) {

                FileNameTextField(
                    value = fileName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 450.dp)
                        .padding(top = 18.dp),
                    onValueChange = { fileName = it }
                ) {

                }


            }

            ActionButton(text = R.string.split_pdf, modifier = Modifier) {}

        }

//        }


        /* Column(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 10.dp, horizontal = 20.dp)
         ) {

             Row(modifier = Modifier.fillMaxWidth()) {

                 Text(
                     modifier = Modifier.weight(1f),
                     text = "My new updated .pdf",
                     style = MaterialTheme.typography.titleMedium
                 )

                 Column(verticalArrangement = Arrangement.Center) {

                     Text(text = "Total pages :")

                     Text(text = "166", style = MaterialTheme.typography.headlineMedium)
                 }

             }

             FileNameTextField(
                 value = fileName, modifier = Modifier
                     .fillMaxWidth(0.9f)
                     .widthIn(max = 450.dp)
                     .padding(top = 10.dp)
             ) {
                 fileName = it
             }


             Button(
                 onClick = {},
                 shape = RoundedCornerShape(6.dp),
                 modifier = Modifier
                     .padding(vertical = 25.dp)
                     .align(Alignment.CenterHorizontally)
             ) {

                 Text(text = "Split PDF", modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp))

             }


         }*/

    }


}


@Preview(showBackground = true)
@Composable
fun PreviewDetailsScreen() {
    DetailsScreen()
}