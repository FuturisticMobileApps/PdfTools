package com.futuristicmobileapps.designsystem.util

fun CharSequence?.isValidString(): Boolean {

    if (this.isNullOrEmpty()) return false

    val textValue = this.toString()

    return textValue.trim { it <= ' ' }.isNotEmpty() &&

            !textValue.trim { it <= ' ' }.equals("null", ignoreCase = true) &&

            !textValue.trim { it <= ' ' }.equals("", ignoreCase = true)

}

fun String?.validateString(): String {

    if (this.isNullOrEmpty()) return ""

    return this.trim()

}

fun String?.isNotValidString(): Boolean = !isValidString()



