package com.obrigada_eu.noteton.presentation.screen_add_note

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrigada_eu.noteton.core.CacheManager
import com.obrigada_eu.noteton.core.util.ImageExifProcessor
import com.obrigada_eu.noteton.domain.model.Note
import com.obrigada_eu.noteton.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val cacheManager: CacheManager,
) : ViewModel() {

    private val _noteScreenData = MutableStateFlow<NoteScreenData>(NoteScreenData())
    val noteScreenData: Flow<NoteScreenData> = _noteScreenData

    private val _rotation = MutableStateFlow<Int>(0)
    val rotation: StateFlow<Int> = _rotation

    private var standardizeJob: Job? = null

    val tempPhotoUri by lazy { cacheManager.tempPhotoUri }


    fun onRotateLeft() {
        _rotation.value = (_rotation.value - 90).mod(360)
    }

    fun onRotateRight() {
        _rotation.value = (_rotation.value + 90).mod(360)
    }

    fun resetRotation() {
        _rotation.value = 0
    }


    fun updateTextFieldValue(newValue: TextFieldValue) {
        _noteScreenData.update { it.copy(textFieldValue = newValue) }
    }

    fun updatePhotoUri(uri: Uri?) {
        _noteScreenData.update { it.copy(photoUri = uri) }
    }

    fun setMode(mode: NoteScreenMode) {
        _noteScreenData.update { it.copy(mode = mode) }
    }

    fun setNoteId(id: Long) {
        _noteScreenData.update { it.copy(noteId = id) }
    }


    fun onSaveNote(id: Long, text: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val success = saveNote(id, text)
            if (success) onSuccess()
        }
    }


    suspend fun saveNote(id: Long, text: String): Boolean {
        standardizeJob?.join()

        val photoUri = _noteScreenData.value.photoUri
        if (text.isBlank() && photoUri == null) return false

        return try {

            withContext(Dispatchers.IO) {

                val updatedPhotoUri = photoUri?.let {
                    editAndSaveImageIfNeeded(it.toFile(), _rotation.value).toUri()
                }

                val note = Note(
                    id = id,
                    text = text,
                    photoPath = updatedPhotoUri?.toString(),
                    updatedAt = System.currentTimeMillis()
                )
                addNoteUseCase(note)
            }

            true

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    fun resetState() {
        _noteScreenData.update { NoteScreenData() }
        resetRotation()
    }

    fun clearTempPhoto() {
        cacheManager.clearTempPhoto()
    }

    fun onPhotoCaptured() {

        standardizeJob = viewModelScope.launch {
            ImageExifProcessor.standardizeImage(tempPhotoUri.toFile())
        }

        if (_noteScreenData.value.photoUri != tempPhotoUri) updatePhotoUri(tempPhotoUri)
    }

    fun editAndSaveImageIfNeeded(
        photoFile: File,
        rotationDegrees: Int
    ): File {
        return if (rotationDegrees % 360 != 0) {
            rotateAndSaveImage(photoFile, rotationDegrees)
        } else {
            val tempFile = cacheManager.tempPhotoFile
            if (photoFile == tempFile) moveTempImageToPermanent(tempFile) else photoFile
        }
    }


    private fun moveTempImageToPermanent(tempFile: File): File {
        val permanentFile = cacheManager.createPermanentImageFile()
        tempFile.copyTo(permanentFile, overwrite = true)
        clearTempPhoto()
        return permanentFile
    }


    private fun rotateAndSaveImage(
        photoFile: File,
        rotationDegrees: Int,
    ): File {
        val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
        val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
        val rotatedBitmap = Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )

        val targetFile = cacheManager.createPermanentImageFile()

        FileOutputStream(targetFile).use { out ->
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }

        bitmap.recycle()
        rotatedBitmap.recycle()

        photoFile.delete()

        return targetFile
    }


    companion object {
        private const val TAG = "AddNoteViewModel"
    }
}