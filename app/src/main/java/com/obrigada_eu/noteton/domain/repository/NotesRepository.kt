package com.obrigada_eu.noteton.domain.repository

import androidx.paging.PagingData
import com.obrigada_eu.noteton.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotesPaged(query: String): Flow<PagingData<Note>>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note)
}