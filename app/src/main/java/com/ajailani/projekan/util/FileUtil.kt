package com.ajailani.projekan.util

import android.content.Context
import java.io.File
import java.io.InputStream
import java.util.*

fun Context.convertInputStreamToFile(inputStream: InputStream): File {
    val file = File(cacheDir, "${UUID.randomUUID()}.jpg")

    inputStream.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return file
}