package com.futuristicmobileapps.tools.screen.tools_dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.futuristicmobileapps.apputil.getSize
import com.futuristicmobileapps.designsystem.component.FileDetailsCard
import com.futuristicmobileapps.designsystem.component.FileNameTextField
import java.io.File

@Composable
fun ToolsDialog(
    contentText: String,
    isSingleButton : Boolean,
    isLoading: Boolean = true,
    file: File,
    fileName: String,
    onFileName: (String) -> Unit,
    onDismiss: () -> Unit,
    onOk: () -> Unit,
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)

    ) {


        OutlinedCard(
            modifier = Modifier.fillMaxWidth(0.95f),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {

                FileDetailsCard(
                    name = file.name,
                    size = file.length().getSize(),
                    modifier = Modifier,
                    isLoading = isLoading
                )

                FileNameTextField(onValueChange = { onFileName(it) }, value = fileName) {

                }

                Divider()

                Text(text = contentText, style = MaterialTheme.typography.bodyMedium)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(onClick = { onDismiss() }) {
                        Text(text = "Cancel")
                    }

                    if (isSingleButton){
                        Button(
                            onClick = { onOk() }
                        ) {
                            Text(text = "Ok")
                        }
                    }

                }


            }


        }


    }
}