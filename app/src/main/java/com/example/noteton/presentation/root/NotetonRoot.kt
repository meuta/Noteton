package com.example.noteton.presentation.root

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteton.R
import com.example.noteton.presentation.screen_add_note.AddNoteScreenHost
import com.example.noteton.presentation.screen_add_note.AddNoteViewModel
import com.example.noteton.presentation.screen_notes_list.NotesListScreenHost
import com.example.noteton.presentation.screen_notes_list.NotesListViewModel
import com.example.noteton.ui.theme.NotetonTheme

enum class NotetonScreen(@StringRes val title: Int) {
    NotesList(title = R.string.notes_list),
    AddNote(title = R.string.add_note)
}

@Composable
fun NotetonRoot(
    notesListViewModel: NotesListViewModel,
    addNoteViewModel: AddNoteViewModel,
    navController: NavHostController = rememberNavController()
) {

//    val backStackEntry by navController.currentBackStackEntryAsState()
//
//    val currentScreen = NotetonScreen.valueOf(
//        backStackEntry?.destination?.route ?: NotetonScreen.NotesList.name
//    )

    NotetonTheme {
        Surface(
            modifier = Modifier.fillMaxSize().imePadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = NotetonScreen.NotesList.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable(route = NotetonScreen.NotesList.name) {
                        NotesListScreenHost(
                            notesListViewModel = notesListViewModel,
                            onAddButtonClick = {
                                navController.navigate(NotetonScreen.AddNote.name)
                                notesListViewModel.resetSearchQuery()
                            },
                        )
                    }

                    composable(route = NotetonScreen.AddNote.name) {
                        AddNoteScreenHost(
                            addNoteViewModel = addNoteViewModel,
                            onSaveButtonClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}