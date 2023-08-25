package com.futuristicMobileApps.pdftools

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale

@Composable
fun WriteExternalStoragePermissionHandler(permission : String = Manifest.permission.WRITE_EXTERNAL_STORAGE, permissionGranted : () -> Unit) {

    val activity : Activity = LocalContext.current as Activity
    // State to track the permission request result
    var permissionState by remember { mutableStateOf(PermissionState.IDLE) }

    // Create the permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = RequestPermission()
    ) { isGranted ->
        permissionState = if (isGranted) {
            PermissionState.GRANTED
        } else {
            // Check if the user permanently denied the permission
            if (!shouldShowRequestPermissionRationale(activity, permission)) {
                PermissionState.PERMANENTLY_DENIED
            } else {
                PermissionState.DENIED
            }
        }
    }

    val settingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
        permissionLauncher.launch(permission)
    }

    // Request the permission when the Composable is first displayed
    LaunchedEffect(true) {
        permissionLauncher.launch(permission)
    }

    // Handle different permission states and show appropriate UI
    when (permissionState) {

        PermissionState.GRANTED -> permissionGranted()

        PermissionState.DENIED -> {

            PermissionDialog(
                title = "Permission Denied",
                content = "Permission is required for further",
                buttonText = "Request permission again"
            ){
                permissionLauncher.launch(permission)
            }

        }

        PermissionState.PERMANENTLY_DENIED -> {
            PermissionDialog(
                title = "Permanently Denied",
                content = "Seems like permission is permanently denied",
                buttonText = "Nav to Settings"
            ){
               openAppSettings(activity,settingsLauncher)
            }
        }

        PermissionState.IDLE -> {
            // Permission request is not yet complete, you can show a loading state if needed
            Text("Requesting Permission...")
        }
    }
}


@Composable
fun PermissionDialog(
    title : String,
    content : String,
    buttonText : String,
    settingsClicked : () -> Unit,
) {

    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = content) },
        confirmButton = {
            Button(onClick = { settingsClicked() }) {
                Text(text = buttonText)
            }
        },
        onDismissRequest = {  }
    )

}


private fun openAppSettings(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    launcher.launch(intent)
}


enum class PermissionState {
    IDLE,
    GRANTED,
    DENIED,
    PERMANENTLY_DENIED
}
