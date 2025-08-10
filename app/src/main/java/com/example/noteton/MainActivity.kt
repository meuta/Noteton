package com.example.noteton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.noteton.presentation.root.NotetonRoot
import com.example.noteton.presentation.screen_add_note.AddNoteViewModel
import com.example.noteton.presentation.screen_notes_list.NotesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val notesListViewModel: NotesListViewModel by viewModels()
    private val addNoteViewModel: AddNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotetonRoot(
                notesListViewModel,
                addNoteViewModel
            )
        }
    }
}
