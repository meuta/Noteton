package com.obrigada_eu.noteton.domain.model

data class Note(
    val id: Long = UNDEFINED_ID,
    val text: String,
    val photoPath: String?,
    val updatedAt: Long
) {
    companion object{
        const val UNDEFINED_ID = 0L
    }
}