package com.futuristicmobileapps.tools.util.file

import java.io.InputStream
import java.io.OutputStream

interface Streamer {
    fun copyFile(inputStream: InputStream, outputStream: OutputStream)
}