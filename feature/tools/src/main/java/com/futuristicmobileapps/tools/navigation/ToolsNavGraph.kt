package com.futuristicmobileapps.tools.navigation

import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.futuristicmobileapps.designsystem.util.validateString
import com.futuristicmobileapps.tools.ToolsScreen
import com.futuristicmobileapps.tools.screen.add_watermark.AddWatermarkScreen
import com.futuristicmobileapps.tools.screen.doc_to_pdf.DocToPdfScreen
import com.futuristicmobileapps.tools.screen.images_to_pdf.ImagesToPdfScreen
import com.futuristicmobileapps.tools.screen.merge.MergeScreen
import com.futuristicmobileapps.tools.screen.page_number.AddPageNumberScreen
import com.futuristicmobileapps.tools.screen.reorder.ReorderScreen
import com.futuristicmobileapps.tools.screen.reorder.RotateScreen
import com.futuristicmobileapps.tools.screen.secure.SecurePdfScreen
import com.futuristicmobileapps.tools.screen.split.SplitScreen

const val ToolsGraph = "tools_graph"

fun NavGraphBuilder.toolsNavGraph(
    navHostController: NavHostController
) {

    navigation(
        startDestination = ToolsScreens.Tools.route,
        route = ToolsGraph
    ) {

        composable(route = ToolsScreens.Tools.route) {
            ToolsScreen(navHostController)
        }

        composable(
            route = ToolsScreens.Split.route,
            arguments = listOf(navArgument(
                URI
            ) {
                type = NavType.StringType
            })
        ) {

            val uri = it.arguments?.getString(URI).validateString().toUri()

            SplitScreen(uri, navHostController)
        }

        composable(route = ToolsScreens.Merge.route) { MergeScreen(navHostController) }

        composable(
            route = ToolsScreens.Rotate.route,
            arguments = listOf(navArgument(URI) { type = NavType.StringType })
        ) {
            val uri = it.arguments?.getString(URI).validateString().toUri()
            RotateScreen(uri = uri, navHostController = navHostController)
        }

        composable(
            route = ToolsScreens.RemoveOrReorder.route,
            arguments = listOf(navArgument(URI) { type = NavType.StringType })
        ) {
            val uri = it.arguments?.getString(URI).validateString().toUri()
            ReorderScreen(uri = uri, navHostController = navHostController)
        }

        composable(
            route = ToolsScreens.AddPageNumber.route,
            arguments = listOf(navArgument(URI) { type = NavType.StringType })
        ) {
            val uri = it.arguments?.getString(URI).validateString().toUri()
            AddPageNumberScreen(uri = uri, navHostController = navHostController)
        }

        composable(route = ToolsScreens.SecurePdf.route,
            arguments = listOf(
                navArgument(URI) { type = NavType.StringType },
                navArgument(isAddPassword) { type = NavType.BoolType }
            )
        ) {
            val uri = it.arguments?.getString(URI).validateString().toUri()
            val isAddPassword = it.arguments?.getBoolean(isAddPassword) ?: false
            SecurePdfScreen(uri = uri, isAddPassword, navHostController = navHostController)
        }

        composable(
            route = ToolsScreens.AddWatermark.route,
            arguments = listOf(navArgument(URI) { type = NavType.StringType })
        ) {
            val uri = it.arguments?.getString(URI).validateString().toUri()
            AddWatermarkScreen(uri = uri, navHostController = navHostController)
        }

        composable(
            route = ToolsScreens.DocToPdf.route,
            arguments = listOf(navArgument(URI) { type = NavType.StringType })
        ) {
            val uri = it.arguments?.getString(URI).validateString().toUri()
            DocToPdfScreen(uri = uri, navHostController = navHostController)
        }

        composable(route = ToolsScreens.ImagesToPdf.route) { ImagesToPdfScreen(navHostController) }

    }

}

