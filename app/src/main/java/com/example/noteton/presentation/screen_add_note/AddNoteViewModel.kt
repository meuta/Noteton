package com.example.noteton.presentation.screen_add_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteton.domain.model.Note
import com.example.noteton.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    fun addNote(text: String, photoPath: String? = null) = viewModelScope.launch {
        val note = Note(
            id = Note.UNDEFINED_ID,
            text = text,
            photoPath = photoPath,
            createdAt = System.currentTimeMillis()
        )
        addNoteUseCase(note)
    }
}