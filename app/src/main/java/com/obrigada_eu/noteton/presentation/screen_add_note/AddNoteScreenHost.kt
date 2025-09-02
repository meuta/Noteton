package com.obrigada_eu.noteton.presentation.screen_add_note

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun AddNoteScreenHost(
    addNoteViewModel: AddNoteViewModel,
    navigateToCameraPreview: () -> Unit,
    popBackStackAndClean: () -> Unit,
) {

    BackHandler {
        popBackStackAndClean()
    }

    val screenData by addNoteViewModel.noteScreenData.collectAsState(NoteScreenData())

    val rotation by addNoteViewModel.rotation.collectAsState(0)


    val wordDefinition by addNoteViewModel.wordDefinitionHtml.collectAsState(null)


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
            addNoteViewModel.showMessage("Required permissions denied")
        }
    }

    AddNoteScreen(
        onValueChange = { newValue ->
            addNoteViewModel.updateTextFieldValue(newValue)
        },
        onExploreButtonClick = addNoteViewModel::fetchDefinitionPage,
        onAddPhotoButtonClick = {
            val permissionsNeeded = permissionsToRequest.filter { permission ->
                ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            }
            if (permissionsNeeded.isEmpty()) {
                navigateToCameraPreview()
            } else {
                permissionLauncher.launch(permissionsNeeded.toTypedArray())
            }
        },
        onSaveButtonClick = {
            addNoteViewModel.onSaveNote(
                id = screenData.noteId,
                text = screenData.textFieldValue.text,
                onSuccess = popBackStackAndClean
            )
        },
        textFieldValue = screenData.textFieldValue,
        photoUri = screenData.photoUri,
        rotation = rotation,
        onRotateLeft = addNoteViewModel::onRotateLeft,
        onRotateRight = addNoteViewModel::onRotateRight,
        definitionHtml = wordDefinition,
        definitionContent = {
            wordDefinition?.let{ DefinitionWebView(it) }
        }
    )


    LaunchedEffect(Unit) {
        addNoteViewModel.uiEvent.collect { text ->
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }
    }
}