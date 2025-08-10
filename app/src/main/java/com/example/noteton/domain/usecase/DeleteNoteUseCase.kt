package com.example.noteton.domain.usecase

import com.example.noteton.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(id: Long) {
        repository.deleteNote(id)
    }
}