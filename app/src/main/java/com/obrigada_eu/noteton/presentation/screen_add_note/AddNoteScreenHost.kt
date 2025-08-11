package com.obrigada_eu.noteton.presentation.screen_add_note

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat

@Composable
fun AddNoteScreenHost(
    addNoteViewModel: AddNoteViewModel,
    navigateToCameraPreview: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {

    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = ""))
    }

    val photoUri by addNoteViewModel.photoUri.collectAsState(null)

    val context = LocalContext.current

    val permissionsToRequest = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else {
        arrayOf(Manifest.permission.CAMERA)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            navigateToCameraPreview()
        } else {
            Toast.makeText(context, "Required permissions denied", Toast.LENGTH_SHORT).show()
        }
    }

    AddNoteScreen(
        onValueChange = { newValue ->
            textFieldValue = newValue
        },
        onAddPhotoButtonClick = {
            val permissionsNeeded = permissionsToRequest.filter { permission ->
                ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
            }
            if (permissionsNeeded.isEmpty()) {
                navigateToCameraPreview()
            } else {
                permissionLauncher.launch(permissionsNeeded.toTypedArray())
            }
        },
        onSaveButtonClick = {
            addNoteViewModel.addNote(textFieldValue.text, photoUri)
            onSaveButtonClick()
        },
        textFieldValue = textFieldValue,
        photoUri = photoUri,
    )
}