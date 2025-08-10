package com.example.noteton.presentation.screen_add_note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddNoteScreenHost(
    addNoteViewModel: AddNoteViewModel,
    onSaveButtonClick: () -> Unit
) {

    var noteText by remember { mutableStateOf("") }

    AddNoteScreen(
        onValueChange = {
            noteText = it
        },
        onSaveButtonClick = {
            addNoteViewModel.addNote(noteText)
            onSaveButtonClick()
        },
        text = noteText
    )
}