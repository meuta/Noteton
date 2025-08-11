package com.obrigada_eu.noteton.presentation.root

import androidx.annotation.StringRes
import com.obrigada_eu.noteton.R

enum class NotetonScreen(@StringRes val title: Int) {
    NotesList(title = R.string.notes_list),
    AddNote(title = R.string.add_note),
    CameraPreview(title = R.string.camera_preview)
}