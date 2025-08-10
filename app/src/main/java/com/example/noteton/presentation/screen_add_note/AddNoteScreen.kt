package com.example.noteton.presentation.screen_add_note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteton.R
import com.example.noteton.ui.theme.NotetonTheme


@Composable
fun AddNoteScreen(
    text: String,
    onValueChange: (String) -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester),
            value = text,
            onValueChange = onValueChange,
            label = { Text(stringResource(R.string.write_a_note)) }
        )
        Button(
            onClick = onSaveButtonClick,
            Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = stringResource(R.string.save)
            )
        }
    }
}


@Preview(showBackground = true,)
@Composable
fun AddNoteScreenPreview() {

    NotetonTheme {
        AddNoteScreen(
            text = "Here is a note preview\nit is visible and it is will be saved in the database",
            onValueChange = {},
        ) {}
    }
}

