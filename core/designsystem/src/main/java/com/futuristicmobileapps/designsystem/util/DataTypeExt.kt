package com.futuristicmobileapps.designsystem.util

fun Int?.validateInt(): Int = this ?: 0
fun Long?.validateLong(): Long = this ?: 0L

fun Double?.validateDouble(): Double = this ?: 0.0

fun String?.isValidInt(): Boolean = this?.toIntOrNull().isValidInt()

fun Int?.isValidInt(): Boolean = validateInt() > 0 && validateInt() < Int.MAX_VALUE

fun Long?.isValidLong(): Boolean = validateLong() > 0

fun Boolean?.validateBoolean(): Boolean = this ?: false

fun String?.stringToInt() : Int = this?.toIntOrNull() ?: 0

fun Int?.intToString() : String = if (isValidInt()) this.toString() else ""


