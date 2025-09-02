package com.obrigada_eu.noteton.data.local.repository

import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.obrigada_eu.noteton.data.db.NoteDao
import com.obrigada_eu.noteton.data.mapper.NoteMapper
import com.obrigada_eu.noteton.domain.model.Note
import com.obrigada_eu.noteton.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.core.net.toUri

class NotesRepositoryImpl(
    private val dao: NoteDao
) : NotesRepository {

    override fun getNotesPaged(query: String): Flow<PagingData<Note>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
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
        dao.getNote(note.id)?.photoPath?.let { uriString ->
            if (uriString != note.photoPath) deletePhotoFromStorage(uriString)
        }
        dao.insert(NoteMapper.mapNoteToDbEntity(note))
    }

    override suspend fun deleteNote(note: Note) {
        note.photoPath?.let { uriString -> deletePhotoFromStorage(uriString) }
        dao.deleteById(note.id)
    }

    private fun deletePhotoFromStorage(uriString: String) {
        try {
            uriString.toUri().toFile().delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}