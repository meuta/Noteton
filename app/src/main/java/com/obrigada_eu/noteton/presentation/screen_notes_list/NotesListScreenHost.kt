package com.obrigada_eu.noteton.presentation.screen_notes_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.obrigada_eu.noteton.domain.model.Note

@Composable
fun NotesListScreenHost(
    notesListViewModel: NotesListViewModel,
    onAddButtonClick: () -> Unit,
    onNoteClick: (Note) -> Unit,
) {

    val notes = notesListViewModel.notes.collectAsLazyPagingItems()

    val isSearchMode by notesListViewModel.searchMode.collectAsState()
    val isDeletableMode by notesListViewModel.deletableMode.collectAsState()

    val searchQuery by notesListViewModel.searchQuery.collectAsState()

    NotesListScreen(
        notes = notes,
        switchSearchMode = { isSearch ->
            notesListViewModel.switchSearchMode(isSearch)
        },
        switchDeleteMode = { isDelete ->
            notesListViewModel.switchDeletableMode(isDelete)
        },
        isSearchMode = isSearchMode,
        isDeletableMode = isDeletableMode,
        onAddButtonClick = onAddButtonClick,
        searchQuery = searchQuery,
        onSearchQueryChanged = notesListViewModel::onSearchQueryChanged,
        onDeleteNoteClick = { notesListViewModel.deleteNote(it) },
        onNoteClick = onNoteClick,
    )
}