package com.obrigada_eu.noteton.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


object ImageExifProcessor {

    private val KEEP_TAGS = setOf(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.TAG_IMAGE_WIDTH,
        ExifInterface.TAG_IMAGE_LENGTH,
        ExifInterface.TAG_PIXEL_X_DIMENSION,
        ExifInterface.TAG_PIXEL_Y_DIMENSION,

        ExifInterface.TAG_FOCAL_LENGTH,
        ExifInterface.TAG_F_NUMBER,
        ExifInterface.TAG_EXPOSURE_TIME,
        ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY,

        ExifInterface.TAG_COLOR_SPACE,
        ExifInterface.TAG_WHITE_BALANCE,
        ExifInterface.TAG_GAMMA,
        ExifInterface.TAG_CONTRAST,
        ExifInterface.TAG_SATURATION,
        ExifInterface.TAG_SHARPNESS,
        ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION,
        ExifInterface.TAG_LIGHT_SOURCE
    )


    suspend fun standardizeImage(file: File) = withContext(Dispatchers.IO) {
        val exif = ExifInterface(file)

        fixOrientation(exif, file)

        exif.javaClass.declaredFields.forEach { field ->
            try {
                val tag = field.get(null) as? String ?: return@forEach
                if (tag !in KEEP_TAGS) {
                    exif.setAttribute(tag, null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        exif.saveAttributes()
    }


    private fun fixOrientation(exif: ExifInterface, file: File) {

        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        val degrees = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }

        if (degrees != 0f) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            val matrix = Matrix()

            matrix.postRotate(degrees)

            val rotatedBitmap = Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )

            FileOutputStream(file).use { out ->
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            rotatedBitmap.recycle()

            exif.setAttribute(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL.toString()
            )

            if (degrees == 90f || degrees == 270f) {

                val width = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
                val length = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
                width?.let { exif.setAttribute(ExifInterface.TAG_IMAGE_LENGTH, it) }
                length?.let { exif.setAttribute(ExifInterface.TAG_IMAGE_WIDTH, it) }

                val xDimension = exif.getAttribute(ExifInterface.TAG_PIXEL_X_DIMENSION)
                val yDimension = exif.getAttribute(ExifInterface.TAG_PIXEL_Y_DIMENSION)
                xDimension?.let { exif.setAttribute(ExifInterface.TAG_PIXEL_Y_DIMENSION, it) }
                yDimension?.let { exif.setAttribute(ExifInterface.TAG_PIXEL_X_DIMENSION, it) }
            }
        }
    }
}



