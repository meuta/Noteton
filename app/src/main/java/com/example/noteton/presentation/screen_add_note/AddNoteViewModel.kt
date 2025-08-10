package com.example.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteton.domain.model.Note
import com.example.noteton.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri: Flow<Uri?> = _photoUri

    fun setPhotoUri(uri: Uri) {
        _photoUri.value = uri
    }

    fun addNote(text: String, photoPath: Uri? = null) = viewModelScope.launch {
        val note = Note(
            id = Note.UNDEFINED_ID,
            text = text,
            photoPath = photoPath?.path,
            createdAt = System.currentTimeMillis()
        )
        addNoteUseCase(note)
    }

    fun resetSearchQuery() {
        _photoUri.value = null
    }
}