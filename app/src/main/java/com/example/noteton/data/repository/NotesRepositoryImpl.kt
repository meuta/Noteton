package com.example.noteton.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.noteton.data.db.NoteDao
import com.example.noteton.data.mapper.NoteMapper
import com.example.noteton.domain.model.Note
import com.example.noteton.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(private val dao: NoteDao) : NotesRepository {

    override fun getNotesPaged(query: String): Flow<PagingData<Note>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { dao.getNotesPagingSource("%$query%") }
        ).flow
            .map { pagingData ->
                pagingData.map { entity ->
                    NoteMapper.mapNoteDbToDomain(entity)
                }
            }
    }

    override suspend fun addNote(note: Note) {
        dao.insert(NoteMapper.mapNoteToDbEntity(note))
    }

    override suspend fun deleteNote(id: Long) {
        dao.deleteById(id)
    }
}