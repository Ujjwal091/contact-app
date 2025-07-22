package com.example.myapplication.presentation.ui.addcontact

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.myapplication.presentation.ui.addcontact.component.AddContactScreenContent
import org.koin.androidx.compose.koinViewModel

/**
 * Screen that allows users to add a new contact or edit an existing one
 *
 * @param contactId The ID of the contact to edit (null if adding a new contact)
 * @param onBackClick Callback when the back button is clicked
 * @param onContactSaved Callback when a contact is successfully added or updated
 * @param viewModel The view model for this screen
 */
@Composable
fun AddContactScreen(
    contactId: String? = null,
    onBackClick: () -> Unit,
    onContactSaved: () -> Unit,
    viewModel: AddContactViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isEditMode = contactId != null

    // Load contact or reset state when entering the screen
    LaunchedEffect(contactId) {
        if (contactId != null) {
            viewModel.loadContact(contactId)
        } else {
            viewModel.resetState()
        }
    }

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // State for tracking unsaved changes
    var hasUnsavedChanges by remember { mutableStateOf(false) }
    var showUnsavedChangesDialog by remember { mutableStateOf(false) }

    // Handle state changes
    LaunchedEffect(state) {
        when (state) {
            is AddContactState.Error -> {
                errorMessage = (state as AddContactState.Error).message
                showErrorDialog = true
            }

            is AddContactState.Success -> {
                onContactSaved()
            }

            is AddContactState.FormError -> {
                // Field errors are handled inline, no dialog needed
            }

            is AddContactState.NotFound -> {
                errorMessage = "Contact not found"
                showErrorDialog = true
            }

            else -> {}
        }
    }

    // Custom back handler that checks for unsaved changes
    val handleBackClick = {
        if (hasUnsavedChanges) {
            // Show confirmation dialog if there are unsaved changes
            showUnsavedChangesDialog = true
        } else {
            // No unsaved changes, just go back
            onBackClick()
        }
    }

    // Handle system back button press
    BackHandler(enabled = true) {
        handleBackClick()
    }

    AddContactScreenContent(
        state = state,
        isEditMode = isEditMode,
        errorMessage = errorMessage,
        showErrorDialog = showErrorDialog,
        showUnsavedChangesDialog = showUnsavedChangesDialog,
        onDismissErrorDialog = { showErrorDialog = false },
        onSaveContact = { name, phone, email, company ->
            viewModel.saveContact(name, phone, email, company) {
                onContactSaved()
            }
        },
        onBackClick = handleBackClick,
        onDiscardChanges = {
            // User confirmed to discard changes, close dialog and go back
            showUnsavedChangesDialog = false
            onBackClick()
        },
        onHasUnsavedChangesChange = { hasChanges ->
            hasUnsavedChanges = hasChanges
        }
    )
}

/**
 * For backward compatibility with existing code
 */
@Composable
fun AddContactScreen(
    onBackClick: () -> Unit,
    onContactAdded: () -> Unit,
    viewModel: AddContactViewModel = koinViewModel()
) {
    // This function maintains backward compatibility by calling the new version
    // with the appropriate parameters
    AddContactScreen(
        contactId = null,
        onBackClick = onBackClick,
        onContactSaved = onContactAdded,
        viewModel = viewModel
    )
}

/**
 * Preview parameter provider for AddContactState
 */
class AddContactStateProvider : PreviewParameterProvider<AddContactState> {
    override val values = sequenceOf(
        AddContactState.Initial,
        AddContactState.Loading,
        AddContactState.Success,
        AddContactState.Error("Failed to add contact")
    )
}

/**
 * Preview of AddContactScreen with different states
 */
@Preview(showBackground = true, group = "Add Contact States")
@Composable
fun AddContactScreenPreview(
    @PreviewParameter(AddContactStateProvider::class) state: AddContactState
) {
    // Create a simple composable that mimics the screen but uses the provided state directly
    AddContactScreenContent(
        state = state,
        isEditMode = false,
        errorMessage = if (state is AddContactState.Error) state.message else "",
        showErrorDialog = state is AddContactState.Error,
        onDismissErrorDialog = { },
        onSaveContact = { _, _, _, _ -> },
        onBackClick = {}
    )
}
