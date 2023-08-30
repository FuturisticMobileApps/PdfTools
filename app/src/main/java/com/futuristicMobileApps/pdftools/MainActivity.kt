package com.futuristicMobileApps.pdftools

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.futuristicmobileapps.designsystem.theme.PdfToolsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var showUI by rememberSaveable { mutableStateOf(false) }

            PdfToolsTheme {

                if (Build.VERSION.SDK_INT < 30) {
                    WriteExternalStoragePermissionHandler {
                        showUI = true
                    }
                } else {
                    showUI = true
                }

                if (showUI)
                    SetUI()
            }

        }

    }

    @Composable
    fun SetUI() {

        val navController = rememberNavController()

        if (isFolderCreated) {

            Scaffold(
                bottomBar = { BottomBar(navController = navController) }
            ) { paddingValues ->

                PdfToolsNavGraph(navHostController = navController, paddingValues)
            }

        } else {
            Text(text = "Something went wrong!")
        }

    }

    private val isFolderCreated: Boolean
        get() {
            val fileDirectory =
                getExternalStoragePublicDirectory("${Environment.DIRECTORY_DOCUMENTS}/PdfFiles")

            if (fileDirectory.exists()) return true

            return fileDirectory.mkdirs()
        }

}

