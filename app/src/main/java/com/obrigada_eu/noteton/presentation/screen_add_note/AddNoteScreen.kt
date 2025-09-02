package com.obrigada_eu.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
    onExploreButtonClick: () -> Unit,
    onAddPhotoButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
    rotation: Int,
    onRotateLeft: () -> Unit,
    onRotateRight: () -> Unit,
    definitionHtml: String?,
    definitionContent: @Composable (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        ->
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

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester),
            onValueChange = onValueChange,
            value = textFieldValue,
            label = { Text(stringResource(R.string.write_a_note)) },
        )

        definitionHtml?.let { text ->

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())

            ) {
                definitionContent(text)
            }
            focusManager.clearFocus()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ->
            Button(
                onClick = onExploreButtonClick,
                contentPadding = PaddingValues(),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 15.dp),
                    maxLines = 1,
                    text = stringResource(
                        R.string.explore_uppercase
                    ),
                )
            }
            Button(
                onClick = onAddPhotoButtonClick,
                contentPadding = PaddingValues(),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 15.dp),
                    maxLines = 1,
                    text = stringResource(
                        R.string.photo_uppercase
                    ),
                )
            }
            Button(
                onClick = onSaveButtonClick,
                contentPadding = PaddingValues(),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 15.dp),
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
    val context = LocalContext.current
    val htmlContent = "<html><body style='font-family: sans-serif;'><div style='margin-bottom:1em;'><b>Grande</b><p style='margin:0.5em 0;'><b>1.</b> adj. <br/>Que tem dimensões mais que ordinárias.<br/>Vasto, extenso: <i>grande território</i>.<br/>Profundo.<br/>Comprido: <i>vara grande</i>.<br/>Crescido, desenvolvido.<br/>Duradoiro.<br/>Importante: <i>grande riqueza</i>.<br/>Poderoso: <i>grande monarca</i>.<br/>Ponderoso, grave: <i>grandes razões</i>.<br/>Desmedido; descomunal.<br/>Heróico.<br/>Copioso.<br/>Intenso: <i>grande nevoeiro</i>.<br/>Bom; magnânimo.<br/>Respeitável: <i>grande sábio</i>.<br/>Magnífico: <i>grande festa</i>.<br/>Numeroso: <i>grande exercito</i>.<br/>Imenso.<br/>Título de certos príncipes soberanos.</p><p style='margin:0.5em 0;'><b>2.</b> Loc. adv. <br/><i>À grande</i>, ou <i>de grande</i>, com magnificência; com largueza.<br/>À maneira dos grandes.</p><p style='margin:0.5em 0;'><b>3.</b> M. <br/>Pessoa nobre, rica, poderosa: <i>ter inveja aos grandes</i>.<br/>Aquele ou aquilo que é grande.</p>(Lat. <i>grandis</i>)</div><hr/></body></html>"

    NotetonTheme {
        AddNoteScreen(
            textFieldValue = TextFieldValue("grande"),
            onValueChange = {},
            onExploreButtonClick = {},
            onAddPhotoButtonClick = {},
            onSaveButtonClick = {},
            photoUri = "android.resource://${context.packageName}/${R.raw.tree1}".toUri(),
            rotation = 0,
            onRotateLeft = {},
            onRotateRight = {},
            definitionHtml = "",
            definitionContent = { DefinitionWebViewPreview(htmlContent) },
        )
    }
}

