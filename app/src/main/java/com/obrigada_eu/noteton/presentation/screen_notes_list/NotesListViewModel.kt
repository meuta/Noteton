package com.obrigada_eu.noteton.presentation.screen_notes_list

import androidx.lifecycle.ViewModel
import androidx.paging.cachedIn
import com.obrigada_eu.noteton.domain.usecase.DeleteNoteUseCase
import com.obrigada_eu.noteton.domain.usecase.GetNotesPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.obrigada_eu.noteton.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val getNotesPagedUseCase: GetNotesPagedUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchMode = MutableStateFlow(false)
    val searchMode: StateFlow<Boolean> = _searchMode.asStateFlow()

    private val _deletableMode = MutableStateFlow(false)
    val deletableMode: StateFlow<Boolean> = _deletableMode.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val notes: Flow<PagingData<Note>> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getNotesPagedUseCase(query)
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun switchSearchMode(isSearch: Boolean) {
        _searchMode.value = isSearch
        if (!isSearch) resetSearchQuery()
    }

    private fun resetSearchQuery() {
        _searchQuery.value = ""
    }

    fun switchDeletableMode(isDelete: Boolean) {
        _deletableMode.value = isDelete
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }
}