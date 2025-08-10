package com.example.noteton.domain.usecase

import androidx.paging.PagingData
import com.example.noteton.domain.model.Note
import com.example.noteton.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesPagedUseCase @Inject constructor(private val repository: NotesRepository) {
    operator fun invoke(query: String): Flow<PagingData<Note>> {
        return repository.getNotesPaged(query)
    }
}