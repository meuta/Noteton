package com.example.noteton.presentation.screen_notes_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import com.example.noteton.domain.model.Note
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.noteton.R
import kotlinx.coroutines.flow.flowOf


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesListScreen(
    notes: LazyPagingItems<Note>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onAddButtonClick: () -> Unit,
    onNoteClick: (Note) -> Unit = {}
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = onAddButtonClick,
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "icon")
            }
        },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = { onSearchQueryChanged(it) },
                label = { Text(stringResource(R.string.search)) }
            )

            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp, 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    count = notes.itemCount,
                ) { index ->
                    val item = notes[index]
                    item?.let {
                        NoteItem(note = it, onClick = { onNoteClick(it) })
                    }
                }

                notes.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true,)
@Composable
fun NotesListScreenPreview() {
    val sampleNotes = listOf(
        Note(id = 1, text = "First note", photoPath = null, createdAt = System.currentTimeMillis()),
        Note(id = 2, text = "Second note", photoPath = null, createdAt = System.currentTimeMillis())
    )

    val fakeFlow = remember {
        flowOf(PagingData.from(sampleNotes))
    }

    val lazyPagingItems = fakeFlow.collectAsLazyPagingItems()

    NotesListScreen(
        notes = lazyPagingItems,
        searchQuery = "",
        onAddButtonClick = {},
        onSearchQueryChanged = {}
    )
}
