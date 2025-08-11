package com.obrigada_eu.noteton.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obrigada_eu.noteton.presentation.screen_camera.CameraPreviewScreen
import com.obrigada_eu.noteton.presentation.screen_add_note.AddNoteScreenHost
import com.obrigada_eu.noteton.presentation.screen_add_note.AddNoteViewModel
import com.obrigada_eu.noteton.presentation.screen_add_note.NoteScreenMode
import com.obrigada_eu.noteton.presentation.screen_notes_list.NotesListScreenHost
import com.obrigada_eu.noteton.presentation.screen_notes_list.NotesListViewModel
import com.obrigada_eu.noteton.ui.theme.NotetonTheme
import androidx.core.net.toUri


@Composable
fun NotetonRoot(
    notesListViewModel: NotesListViewModel,
    addNoteViewModel: AddNoteViewModel,
    navController: NavHostController = rememberNavController(),
) {

    NotetonTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = NotetonScreen.NotesList.name,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    composable(route = NotetonScreen.NotesList.name) {
                        NotesListScreenHost(
                            notesListViewModel = notesListViewModel,
                            onAddButtonClick = {
                                addNoteViewModel.setMode(NoteScreenMode.CREATE)
                                navController.navigate(NotetonScreen.AddNote.name)
                                notesListViewModel.resetState()
                            },
                            onNoteClick = { note ->
                                navController.navigate(
                                    NotetonScreen.AddNote.name,
                                    navigatorExtras = null
                                )
                                notesListViewModel.resetState()
                                addNoteViewModel.setMode(NoteScreenMode.EDIT)
                                addNoteViewModel.updateTextFieldValue(TextFieldValue(
                                    text = note.text,
                                    selection = TextRange(note.text.length)
                                ))
                                addNoteViewModel.setPhotoUri(note.photoPath?.toUri())
                                addNoteViewModel.setNoteId(note.id)
                            },
                        )
                    }

                    composable(route = NotetonScreen.AddNote.name) {

                        AddNoteScreenHost(
                            addNoteViewModel = addNoteViewModel,
                            navigateToCameraPreview = {
                                navController.navigate(NotetonScreen.CameraPreview.name)
                            },
                            onSaveButtonClick = {
                                navController.popBackStack()
                                addNoteViewModel.resetState()
                            },
                        )
                    }

                    composable(route = NotetonScreen.CameraPreview.name) {
                        CameraPreviewScreen(
                            onPhotoCaptured = { photoUri ->
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("captured_photo", photoUri)
                                navController.popBackStack()
                                addNoteViewModel.setPhotoUri(photoUri)
                            },
                        )
                    }
                }
            }
        }
    }
}


