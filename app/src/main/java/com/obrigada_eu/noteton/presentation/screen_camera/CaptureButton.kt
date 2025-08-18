package com.obrigada_eu.noteton.presentation.screen_camera

import android.view.MotionEvent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CaptureButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }

    val innerCircleSize by animateDpAsState(
        targetValue = if (pressed) 36.dp else 48.dp,
        animationSpec = tween(durationMillis = 120),
        label = "InnerCircleSize",
    )

    Box(
        modifier = modifier
            .size(72.dp)
            .border(3.dp, Color.White, shape = CircleShape)
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        pressed = true
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        pressed = false
                        onClick()
                        true
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        pressed = false
                        true
                    }
                    else -> false
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(innerCircleSize)) {
            drawCircle(
                color = Color.White,
                radius = size.width / 2
            )
        }
    }
}

@Preview
@Composable
fun CaptureButtonPreview() {
    CaptureButton(
        modifier = Modifier,
        onClick = {},
    )
}