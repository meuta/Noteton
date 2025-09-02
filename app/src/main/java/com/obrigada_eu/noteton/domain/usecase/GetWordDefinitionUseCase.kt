package com.obrigada_eu.noteton.domain.usecase

import com.obrigada_eu.noteton.domain.repository.DictionaryRepository
import javax.inject.Inject

class GetWordDefinitionUseCase @Inject constructor(private val repository: DictionaryRepository) {
    suspend operator fun invoke(word: String): String? {
        return repository.getWordDefinitionHtml(word)
    }
}
