package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.presentation.ui.addcontact.AddContactState

/**
 * Content wrapper for the add/edit contact screen that includes the scaffold, content, and dialogs
 *
 * @param state The current state of the add/edit contact screen
 * @param isEditMode Whether the screen is in edit mode
 * @param errorMessage The error message to display in the error dialog (if state is Error)
 * @param showErrorDialog Whether to show the error dialog
 * @param showUnsavedChangesDialog Whether to show the unsaved changes dialog
 * @param onDismissErrorDialog Callback when the error dialog is dismissed
 * @param onSaveContact Callback when a contact is saved with name, phone, email, and company
 * @param onBackClick Callback when the back button is clicked
 * @param onDiscardChanges Callback when the user confirms discarding changes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreenContent(
    state: AddContactState,
    isEditMode: Boolean = false,
    errorMessage: String = "",
    showErrorDialog: Boolean = state is AddContactState.Error,
    showUnsavedChangesDialog: Boolean = false,
    onDismissErrorDialog: () -> Unit = {},
    onSaveContact: (name: String, phone: String, email: String, company: String) -> Unit,
    onBackClick: () -> Unit,
    onDiscardChanges: () -> Unit = {},
    onHasUnsavedChangesChange: (Boolean) -> Unit = {}
) {
    Scaffold(
        topBar = {
            AddContactTopBar(
                isEditMode = isEditMode,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AddContactContent(
                state = state,
                isEditMode = isEditMode,
                onSaveContact = onSaveContact,
                onBackClick = onBackClick,
                onHasUnsavedChanges = onHasUnsavedChangesChange
            )
        }
    }

    // Error dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = onDismissErrorDialog,
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = onDismissErrorDialog) {
                    Text("OK")
                }
            }
        )
    }

    // Unsaved changes dialog
    if (showUnsavedChangesDialog) {
        UnsavedChangesDialog(
            onDismiss = { onHasUnsavedChangesChange(true) }, // Continue editing
            onConfirm = onDiscardChanges // Discard changes and go back
        )
    }
}

/**
 * For backward compatibility with existing code
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreenContent(
    state: AddContactState,
    errorMessage: String = "",
    showErrorDialog: Boolean = state is AddContactState.Error,
    onDismissErrorDialog: () -> Unit = {},
    onAddContact: (name: String, phone: String, email: String, company: String) -> Unit,
    onBackClick: () -> Unit
) {
    AddContactScreenContent(
        state = state,
        isEditMode = false,
        errorMessage = errorMessage,
        showErrorDialog = showErrorDialog,
        showUnsavedChangesDialog = false,
        onDismissErrorDialog = onDismissErrorDialog,
        onSaveContact = onAddContact,
        onBackClick = onBackClick,
        onDiscardChanges = onBackClick,
        onHasUnsavedChangesChange = { _ -> }
    )
}