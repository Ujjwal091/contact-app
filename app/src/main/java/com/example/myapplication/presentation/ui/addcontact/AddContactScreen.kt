package com.example.myapplication.presentation.ui.addcontact

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.myapplication.presentation.ui.addcontact.component.AddContactScreenContent
import org.koin.androidx.compose.koinViewModel

/**
 * Screen that allows users to add a new contact
 *
 * @param onBackClick Callback when the back button is clicked
 * @param onContactAdded Callback when a contact is successfully added
 * @param viewModel The view model for this screen
 */
@Composable
fun AddContactScreen(
    onBackClick: () -> Unit,
    onContactAdded: () -> Unit,
    viewModel: AddContactViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Reset state when entering the screen
    LaunchedEffect(Unit) {
        viewModel.resetState()
    }

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Handle state changes
    LaunchedEffect(state) {
        when (state) {
            is AddContactState.Error -> {
                errorMessage = (state as AddContactState.Error).message
                showErrorDialog = true
            }

            is AddContactState.Success -> {
                onContactAdded()
            }

            else -> {}
        }
    }

    AddContactScreenContent(
        state = state,
        errorMessage = errorMessage,
        showErrorDialog = showErrorDialog,
        onDismissErrorDialog = { showErrorDialog = false },
        onAddContact = { name, phone ->
            viewModel.addContact(name, phone) {
                onContactAdded()
            }
        },
        onBackClick = onBackClick
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
    MaterialTheme {
        AddContactScreenContent(
            state = state,
            errorMessage = if (state is AddContactState.Error) state.message else "",
            showErrorDialog = state is AddContactState.Error,
            onDismissErrorDialog = { },
            onAddContact = { _, _ -> },
            onBackClick = {}
        )
    }
}
