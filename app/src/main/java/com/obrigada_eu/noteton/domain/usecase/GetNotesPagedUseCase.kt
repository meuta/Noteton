package com.obrigada_eu.noteton.domain.usecase

import androidx.paging.PagingData
import com.obrigada_eu.noteton.domain.model.Note
import com.obrigada_eu.noteton.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesPagedUseCase @Inject constructor(private val repository: NotesRepository) {
    operator fun invoke(query: String): Flow<PagingData<Note>> {
        return repository.getNotesPaged(query)
    }
}