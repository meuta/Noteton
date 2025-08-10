package com.example.noteton.presentation.screen_notes_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun NotesListScreenHost(
    notesListViewModel: NotesListViewModel,
    onAddButtonClick: () -> Unit
) {

    val searchQuery by notesListViewModel.searchQuery.collectAsState()
    val notes = notesListViewModel.notes.collectAsLazyPagingItems()

    NotesListScreen(
        notes = notes,
        searchQuery = searchQuery,
        onSearchQueryChanged = notesListViewModel::onSearchQueryChanged,
        onAddButtonClick = onAddButtonClick,
        onNoteClick = {}
    )
}