package com.obrigada_eu.noteton.core

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File

class CacheManager(@ApplicationContext private val context: Context) {

    val tempPhotoFile: File by lazy { context.cacheDir.resolve("tempPhotoFile.jpg") }
    val tempPhotoUri: Uri by lazy { tempPhotoFile.toUri() }

    fun createPermanentImageFile(): File {
        val dir = context.filesDir.resolve("images").apply { mkdirs() }
        return dir.resolve("${System.currentTimeMillis()}.jpg")
    }

    fun clearTempPhoto() {
        try {
            tempPhotoFile.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}