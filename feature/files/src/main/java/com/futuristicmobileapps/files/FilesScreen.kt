package com.futuristicmobileapps.files

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.futuristicmobileapps.files.util.FilesConstants
import com.futuristicmobileapps.apputil.getSize
import com.futuristicmobileapps.apputil.toDate
import com.futuristicmobileapps.itext.ITextConstants
import java.io.File

@Composable
fun FilesScreen() {

    GetAllPdfs()

}

@Composable
fun GetAllPdfs() {

    val context = LocalContext.current

    val pdfFiles = getPdfFiles()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {

        pdfFiles?.let { pdfItems ->

            items(pdfItems) { pdfItem ->

                PdfItem(context = context, it = pdfItem)
            }
        }

    }
}


@Composable
fun PdfItem(context: Context, it: File) {

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
            .clickable { openFileInternal(context, it) }
    ) {
        Text(
            text = it.name,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text(text = it.lastModified().toDate(), modifier = Modifier.weight(1f))

            Text(text = "${it.length().getSize()} MB")
        }

    }
}

fun getPdfFiles(): List<File>? {

    val documentsDir = ITextConstants.fileDirectory

    val files = File(documentsDir.path).listFiles()

    val pdfFiles = files?.filter {
        it.name.endsWith(ITextConstants.pdf)
    }

    return pdfFiles
}



private fun openFileInternal(
    context: Context,
    file: File,
    dataType: String = FilesConstants.pdfDataType
) {
    val target = Intent(Intent.ACTION_VIEW)
    target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    try {
        val uri = FileProvider.getUriForFile(
            context,
            FilesConstants.fileProvider,
            file
        )

        target.setDataAndType(uri, dataType)
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(target, FilesConstants.openFile))
    } catch (e: Exception) {
        e.printStackTrace()
        Log.i("Error", "openFileInternal: Something went wrong!")
    }
}

