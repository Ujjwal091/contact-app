package com.example.myapplication.presentation.ui.contactdetail.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


/**
 * Dialog for confirming contact deletion
 *
 * @param onDismiss Callback when the dialog is dismissed
 * @param onConfirm Callback when the deletion is confirmed
 */
@Composable
fun DeleteContactDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Contact") },
        text = { Text("Are you sure you want to delete this contact?") },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DeleteContactDialogPreview() {

    DeleteContactDialog(
        onDismiss = {},
        onConfirm = {}
    )
}
