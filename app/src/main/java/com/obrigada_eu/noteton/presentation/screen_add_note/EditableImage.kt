package com.obrigada_eu.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.RotateLeft
import androidx.compose.material.icons.automirrored.filled.RotateRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.AsyncImage

@Composable
fun EditableImage(
    uri: Uri,
    contentDescription: String,
    modifier: Modifier = Modifier,
    rotation: Int,
    onRotateLeft: () -> Unit,
    onRotateRight: () -> Unit,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = uri,
            contentDescription = contentDescription,
            modifier = modifier
                .graphicsLayer {
                    rotationZ = rotation.toFloat()
                }
        )
        Row {
            IconButton(onClick = onRotateLeft) {
                Icon(Icons.AutoMirrored.Filled.RotateLeft, contentDescription = "Rotate left")
            }
            IconButton(onClick = onRotateRight) {
                Icon(Icons.AutoMirrored.Filled.RotateRight, contentDescription = "Rotate right")
            }
        }
    }
}

@Preview
@Composable
fun EditableImagePreview() {
    EditableImage(
        uri = "".toUri(),
        contentDescription = "",
        rotation = 0,
        onRotateLeft = {},
        onRotateRight = {}
    )
}
