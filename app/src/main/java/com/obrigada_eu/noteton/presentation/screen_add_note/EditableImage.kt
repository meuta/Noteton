package com.obrigada_eu.noteton.presentation.screen_add_note

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.RotateLeft
import androidx.compose.material.icons.automirrored.filled.RotateRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.obrigada_eu.noteton.R


@Composable
fun EditableImage(
    uri: Uri,
    contentDescription: String,
    modifier: Modifier = Modifier,
    rotation: Int,
    onRotateLeft: () -> Unit,
    onRotateRight: () -> Unit,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.height(16.dp))

        PinchToZoomImage(
            imageUrl = uri,
            contentDescription = contentDescription,
            modifier = modifier
                .weight(1f, false)
                .height(180.dp)
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

@Preview(showBackground = true)
@Composable
fun EditableImagePreview() {
    val context = LocalContext.current

    EditableImage(
        uri = "android.resource://${context.packageName}/${R.raw.tree1}".toUri(),
        contentDescription = "",
        rotation = 0,
        onRotateLeft = {},
        onRotateRight = {}
    )
}
