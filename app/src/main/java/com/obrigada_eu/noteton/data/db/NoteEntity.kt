package com.obrigada_eu.noteton.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val photoPath: String?,
    val createdAt: Long
)
