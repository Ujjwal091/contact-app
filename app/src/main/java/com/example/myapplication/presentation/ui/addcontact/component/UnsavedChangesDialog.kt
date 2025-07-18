package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Dialog shown when the user tries to navigate back with unsaved changes
 *
 * @param onDismiss Callback when the dialog is dismissed (continue editing)
 * @param onConfirm Callback when the user confirms discarding changes
 */
@Composable
fun UnsavedChangesDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Unsaved Changes") },
        text = { Text("You have unsaved changes. Are you sure you want to discard them?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Discard")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Continue")
            }
        }
    )
}

@Preview
@Composable
fun UnsavedChangesDialogPreview() {
    MaterialTheme {
        UnsavedChangesDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}