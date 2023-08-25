package com.futuristicmobileapps.tools.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PdfTool(
    val action: Action,
    @StringRes val label : Int,
    @DrawableRes val icon: Int
)
