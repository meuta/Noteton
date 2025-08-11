package com.obrigada_eu.noteton.domain.usecase

import com.obrigada_eu.noteton.domain.model.Note
import com.obrigada_eu.noteton.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}