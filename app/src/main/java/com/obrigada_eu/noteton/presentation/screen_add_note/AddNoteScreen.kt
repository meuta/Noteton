package com.obrigada_eu.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.obrigada_eu.noteton.R
import com.obrigada_eu.noteton.ui.theme.NotetonTheme


@Composable
fun AddNoteScreen(
    textFieldValue: TextFieldValue,
    photoUri: Uri?,
    onValueChange: (TextFieldValue) -> Unit,
    onAddPhotoButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
    rotation: Int,
    onRotateLeft: () -> Unit,
    onRotateRight: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        photoUri?.let { uri ->
            EditableImage(
                uri = uri,
                contentDescription = "Photo of the Note",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                rotation = rotation,
                onRotateLeft = onRotateLeft,
                onRotateRight = onRotateRight,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester),
            onValueChange = onValueChange,
            value = textFieldValue,
            label = { Text(stringResource(R.string.write_a_note)) },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button(
                onClick = onAddPhotoButtonClick,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    maxLines = 1,
                    text = stringResource(
                        if (photoUri == null) R.string.add_photo_uppercase else R.string.change_photo_uppercase
                    ),
                )
            }
            Button(
                onClick = onSaveButtonClick,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    maxLines = 1,
                    text = stringResource(R.string.save_uppercase),
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddNoteScreenPreview() {

    NotetonTheme {
        AddNoteScreen(
            textFieldValue = TextFieldValue("Here is a note preview\nit is visible and it is will be saved in the database"),
            onValueChange = {},
            onAddPhotoButtonClick = {},
            onSaveButtonClick = {},
            photoUri = "".toUri(),
            rotation = 0,
            onRotateLeft = {},
            onRotateRight = {},
        )
    }
}

