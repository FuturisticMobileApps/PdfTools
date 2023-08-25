package com.futuristicmobileapps.apputil

import java.io.File
import java.text.DecimalFormat


fun Long.getSize() = convertKbToMbAndRoundOff()

fun Long.convertKbToMbAndRoundOff(): String {
    val megabytes = this.convertKbToMb()
    return DecimalFormat("#.##").format(megabytes)
}

fun Long.convertKbToMb(): Double {
    return this.toDouble() / (1024.0 * 1024.0)
}
