package com.futuristicMobileApps.pdftools

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Environment
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.documentfile.provider.DocumentFile

@Composable
fun CheckDocument() {

/*

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        putExtra(
            DocumentsContract.EXTRA_INITIAL_URI,
            Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DOCUMENTS}/pdfFiles")
        )
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->

            if (activityResult.resultCode == RESULT_OK) {

                val directoryUri = activityResult.data?.data
                    ?: return@rememberLauncherForActivityResult

//                        contentResolver.takePersistableUriPermission(directoryUri,Intent.FLAG_GRANT_READ_URI_PERMISSION)

                val documentsTree = DocumentFile.fromTreeUri(getApplication(), directoryUri) ?: return@rememberLauncherForActivityResult
                val childDocuments = documentsTree.listFiles()

                childDocuments.forEach {
                    println(it.name)
                }

            }

        }


    Button(onClick = { launcher.launch(intent) }) {
        Text(text = "Click here")
    }
*/

}