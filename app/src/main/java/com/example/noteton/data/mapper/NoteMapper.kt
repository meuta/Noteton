package com.example.noteton.data.mapper

import com.example.noteton.data.db.NoteEntity
import com.example.noteton.domain.model.Note

object NoteMapper {
    fun mapNoteToDbEntity(note: Note): NoteEntity = NoteEntity(
        id = note.id,
        text = note.text,
        photoPath = note.photoPath,
        createdAt = note.createdAt
    )
    fun mapNoteDbToDomain(note: NoteEntity): Note = Note(
        id = note.id,
        text = note.text,
        photoPath = note.photoPath,
        createdAt = note.createdAt
    )
}