package com.example.noteton.presentation.screen_notes_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteton.domain.model.Note

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Card(onClick = onClick) {
        SelectionContainer {
            Text(
                modifier = Modifier.padding(8.dp),
                text = note.text
            )
        }
    }
}