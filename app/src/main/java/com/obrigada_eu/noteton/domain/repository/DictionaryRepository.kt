package com.obrigada_eu.noteton.domain.repository


interface DictionaryRepository {

    suspend fun getWordDefinitionHtml(word: String): String?
}