package com.obrigada_eu.noteton.presentation.screen_notes_list

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun TextSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
        )
        Switch(
            modifier = Modifier.scale(0.8f),
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextSwitchPreview() {
    TextSwitch("SEARCH", true) { }
}