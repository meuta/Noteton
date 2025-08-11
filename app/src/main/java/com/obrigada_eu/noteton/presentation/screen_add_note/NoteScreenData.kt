package com.obrigada_eu.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.obrigada_eu.noteton.domain.model.Note

enum class NoteScreenMode { CREATE, EDIT }

data class NoteScreenData(
    val mode: NoteScreenMode = NoteScreenMode.CREATE,
    val textFieldValue: TextFieldValue = TextFieldValue(""),
    val photoUri: Uri? = null,
    val noteId: Long = Note.UNDEFINED_ID,
)

