package com.obrigada_eu.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.obrigada_eu.noteton.core.CacheManager
import com.obrigada_eu.noteton.domain.model.Note
import com.obrigada_eu.noteton.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _noteScreenData = MutableStateFlow<NoteScreenData>(NoteScreenData())
    val noteScreenData: Flow<NoteScreenData> = _noteScreenData

    val tempPhotoUri by lazy { cacheManager.tempPhotoUri }

    fun updateTextFieldValue(newValue: TextFieldValue) {
        _noteScreenData.update { it.copy(textFieldValue = newValue) }
    }

    fun updatePhotoUri(uri: Uri?) { _noteScreenData.update { it.copy(photoUri = uri) } }

    fun setMode(mode: NoteScreenMode) { _noteScreenData.update { it.copy(mode = mode) } }
    fun setNoteId(id: Long) { _noteScreenData.update { it.copy(noteId = id) } }

    suspend fun saveNote(id: Long, text: String): Boolean {
        var photoUri = _noteScreenData.value.photoUri
        if (text.isBlank() && photoUri == null) return false
        return try {

            photoUri = photoUri?.let {
                withContext(Dispatchers.IO) {
                    val tempFile = cacheManager.tempPhotoFile
                    if (tempFile.exists()) moveTempImageToPermanent(tempFile).toUri() else it
                }
            }
            val note = Note(
                id = id,
                text = text,
                photoPath = photoUri?.toString(),
                createdAt = System.currentTimeMillis()
            )
            addNoteUseCase(note)
            resetState()
            true

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    fun resetState() {
        _noteScreenData.update { NoteScreenData() }
    }

    fun clearTempPhoto() {
        cacheManager.clearTempPhoto()
    }

    fun onPhotoCaptured() {
        if (_noteScreenData.value.photoUri != tempPhotoUri) updatePhotoUri(tempPhotoUri)
    }

    private fun moveTempImageToPermanent(tempFile: File): File {
        val permanentFile = cacheManager.createPermanentImageFile()
        tempFile.copyTo(permanentFile, overwrite = true)
        clearTempPhoto()
        return permanentFile
    }
}