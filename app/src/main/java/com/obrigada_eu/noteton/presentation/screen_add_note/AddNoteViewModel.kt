package com.obrigada_eu.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrigada_eu.noteton.domain.model.Note
import com.obrigada_eu.noteton.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {


    private val _noteScreenData = MutableStateFlow<NoteScreenData>(NoteScreenData())
    val noteScreenData: Flow<NoteScreenData> = _noteScreenData

    fun addNote(id: Long, text: String, photoPath: Uri? = null) = viewModelScope.launch {
        val note = Note(
            id = id,
            text = text,
            photoPath = photoPath?.path,
            createdAt = System.currentTimeMillis()
        )
        addNoteUseCase(note)
    }

    fun updateTextFieldValue(newValue: TextFieldValue) {
        _noteScreenData.update { it.copy(textFieldValue = newValue) }
    }
    fun setPhotoUri(uri: Uri?) { _noteScreenData.update { it.copy(photoUri = uri) } }

    fun setMode(mode: NoteScreenMode) { _noteScreenData.update { it.copy(mode = mode) } }
    fun setNoteId(id: Long) { _noteScreenData.update { it.copy(noteId = id) } }

    fun resetState() {
        _noteScreenData.update { NoteScreenData()}
    }
}