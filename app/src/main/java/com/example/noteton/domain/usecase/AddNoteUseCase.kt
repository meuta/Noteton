package com.example.noteton.domain.usecase


import com.example.noteton.domain.model.Note
import com.example.noteton.domain.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(note: Note) {
        repository.addNote(note)
    }
}