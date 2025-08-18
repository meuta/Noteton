package com.obrigada_eu.noteton.presentation.screen_camera

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.core.net.toUri

@Composable
fun CameraBox(
    modifier: Modifier,
    previewView: View,
    tempPhotoUri: Uri,
    imageCapture: ImageCapture,
    onPhotoCaptured: () -> Unit,
) {

    val context = LocalContext.current

    var flashVisible by remember { mutableStateOf(false) }

    val flashAlpha by animateFloatAsState(
        targetValue = if (flashVisible) 0.4f else 0f,
        animationSpec = tween(durationMillis = 100),
        finishedListener = {
            if (flashVisible) flashVisible = false
        },
        label = "FlashAlpha"
    )

    Box(modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        if (flashAlpha > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = flashAlpha))
            )
        }

        CaptureButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClick = {
                flashVisible = true

                val outputOptions = ImageCapture.OutputFileOptions.Builder(tempPhotoUri.toFile()).build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            onPhotoCaptured()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            exception.printStackTrace()
                        }
                    }
                )
            }
        )
    }
}

@SuppressLint("SetTextI18n")
@Preview
@Composable
fun CameraBoxPreview() {
    CameraBox(
        previewView = View(LocalContext.current),
        tempPhotoUri = "".toUri(),
        imageCapture = ImageCapture.Builder().build(),
        onPhotoCaptured = {},
        modifier = Modifier,
    )
}