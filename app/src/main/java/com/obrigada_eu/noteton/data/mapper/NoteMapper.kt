package com.obrigada_eu.noteton.data.mapper

import com.obrigada_eu.noteton.data.db.NoteEntity
import com.obrigada_eu.noteton.domain.model.Note

object NoteMapper {
    fun mapNoteToDbEntity(note: Note): NoteEntity = NoteEntity(
        id = note.id,
        text = note.text,
        photoPath = note.photoPath,
        updatedAt = note.updatedAt
    )
    fun mapNoteDbToDomain(note: NoteEntity): Note = Note(
        id = note.id,
        text = note.text,
        photoPath = note.photoPath,
        updatedAt = note.updatedAt
    )
}