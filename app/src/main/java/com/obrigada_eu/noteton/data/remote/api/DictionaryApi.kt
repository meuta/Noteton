package com.obrigada_eu.noteton.data.remote.api

import com.obrigada_eu.noteton.data.remote.model.WordEntryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("word/{word}")
    suspend fun getWordEntries(@Path("word") word: String): List<WordEntryDto>?
}