package com.obrigada_eu.noteton.data.repository

import android.content.Context
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
import java.io.File
import androidx.core.net.toUri

class NotesRepositoryImpl(
    private val context: Context,
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
        dao.insert(NoteMapper.mapNoteToDbEntity(note))
    }

    override suspend fun deleteNote(note: Note) {
        note.photoPath?.let { uriString ->
            try {
                val uri = uriString.toUri()
                context.contentResolver.delete(uri, null, null)
                File(uri.path!!).delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        dao.deleteById(note.id)
    }
}