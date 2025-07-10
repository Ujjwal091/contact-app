package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.presentation.ui.addcontact.AddContactState

/**
 * Content wrapper for the add contact screen that includes the scaffold, content, and error dialog
 *
 * @param state The current state of the add contact screen
 * @param errorMessage The error message to display in the error dialog (if state is Error)
 * @param showErrorDialog Whether to show the error dialog
 * @param onDismissErrorDialog Callback when the error dialog is dismissed
 * @param onAddContact Callback when a contact is added with name and phone
 * @param onBackClick Callback when the back button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreenContent(
    state: AddContactState,
    errorMessage: String = "",
    showErrorDialog: Boolean = state is AddContactState.Error,
    onDismissErrorDialog: () -> Unit = {},
    onAddContact: (name: String, phone: String) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AddContactTopBar(
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
                onAddContact = onAddContact,
                onBackClick = onBackClick
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
}