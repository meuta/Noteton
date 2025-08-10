package com.example.noteton.domain.model

data class Note(
    val id: Long = UNDEFINED_ID,
    val text: String,
    val photoPath: String?,
    val createdAt: Long
) {
    companion object{
        const val UNDEFINED_ID = 0L
    }
}