package com.futuristicMobileApps.pdftools

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.files.FilesScreen
import com.futuristicmobileapps.settings.SettingsScreen
import com.futuristicmobileapps.tools.navigation.toolsNavGraph

// route
private object Route {
    const val tools = "tools_graph"
    const val files = "files"
    const val settings = "settings"
}


// label
private object Label {
    const val Tools = "Tools"
    const val Files = "Files"
    const val Settings = "Settings"
}


sealed class BottomAppBarScreens(
    val route: String,
    val label: String,
    @DrawableRes val drawable: Int
) {

    object Tools : BottomAppBarScreens(
        route = Route.tools,
        label = Label.Tools,
        drawable = R.drawable.build_selected
    )

    object Files : BottomAppBarScreens(
        route = Route.files,
        label = Label.Files,
        drawable = R.drawable.files_selected
    )

    object Settings : BottomAppBarScreens(
        route = Route.settings,
        label = Label.Settings,
        drawable = R.drawable.settings_selected
    )
}


@Composable
fun PdfToolsNavGraph(navHostController: NavHostController, paddingValues: PaddingValues) {

    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navHostController,
        startDestination = BottomAppBarScreens.Tools.route
    ) {

        toolsNavGraph(navHostController)

        composable(route = BottomAppBarScreens.Files.route) {
            FilesScreen()
        }

        composable(route = BottomAppBarScreens.Settings.route) {
            SettingsScreen()
        }


    }
}