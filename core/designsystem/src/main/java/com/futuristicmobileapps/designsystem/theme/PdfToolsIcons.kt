package com.futuristicmobileapps.designsystem.theme

import com.futuristicmobileapps.designsystem.R

class PdfToolsIcons(private val isNightTheme: Boolean = false) {

    val merge: Int
        get() = if (isNightTheme) R.drawable.merge_icon_night else R.drawable.merge_icon_day

    val split: Int
        get() = if (isNightTheme) R.drawable.split_icon_night else R.drawable.split_icon_day

    val rotate: Int
        get() = if (isNightTheme) R.drawable.rotate_icon_night else R.drawable.rotate_icon_day
}