package com.obrigada_eu.noteton.presentation.screen_camera

import android.net.Uri
import androidx.compose.runtime.Composable

@Composable
fun CameraPreviewHost(
    tempPhotoUri: Uri,
    onPhotoCaptured: () -> Unit,
) {
    CameraPreviewScreen(
        tempPhotoUri = tempPhotoUri,
        onPhotoCaptured = onPhotoCaptured
    )
}