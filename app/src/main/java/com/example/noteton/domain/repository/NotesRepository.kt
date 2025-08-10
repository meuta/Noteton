package com.example.noteton.domain.repository

import androidx.paging.PagingData
import com.example.noteton.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotesPaged(query: String): Flow<PagingData<Note>>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(id: Long)
}