package com.obrigada_eu.noteton.presentation.screen_notes_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.obrigada_eu.noteton.domain.model.Note

@Composable
fun NoteItem(
    note: Note,
    deletable: Boolean,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Card(onClick = onItemClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            if (deletable) {
                Button(
                    modifier = Modifier.size(36.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = onDeleteClick,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "icon",
                    )
                }
            }


            SelectionContainer(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
            ) {
                Text(
                    text = note.text,
                )
            }
            note.photoPath?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Photo of the Note",
                    modifier = Modifier
                        .width(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    NoteItem(
        note = Note(
            text = "note text text text text",
            photoPath = "",
            createdAt = 0,
        ),
        onDeleteClick = { },
        deletable = true,
        onItemClick = {},
    )
}