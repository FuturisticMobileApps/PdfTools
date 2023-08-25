package com.futuristicmobileapps.tools.navigation

import android.net.Uri


const val URI = "uri"
const val isAddPassword = "isAddPassword"

sealed class ToolsScreens(val route: String) {

    object Tools : ToolsScreens("tools")

    object Rotate : ToolsScreens("rotate/{uri}") {
        fun setUri(uri: String): String = "rotate/$uri"
    }

    object RemoveOrReorder : ToolsScreens("remove_or_reorder/{uri}") {
        fun setUri(uri: String): String = "remove_or_reorder/$uri"
    }

    object Split : ToolsScreens("split/{uri}") {
        fun setUri(uri: String): String = "split/$uri"
    }

    object Merge : ToolsScreens("merge")

    object AddPageNumber : ToolsScreens("pageNumber/{uri}") {
        fun setUri(uri: String): String = "pageNumber/$uri"
    }

    object SecurePdf : ToolsScreens("pageNumber/{uri}/{isAddPassword}") {
        fun setUriAndAddPassword(uri: String,isAddPassword : Boolean): String = "pageNumber/$uri/$isAddPassword"
    }

    object AddWatermark : ToolsScreens("addWatermark/{uri}") {
        fun setUri(uri: String): String = "addWatermark/$uri"
    }

    object DocToPdf : ToolsScreens("docToPdf/{uri}") {
        fun setUri(uri: String): String = "docToPdf/$uri"
    }

    object ImagesToPdf : ToolsScreens("imagesToPdf")

}
