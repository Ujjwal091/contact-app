package com.example.myapplication.presentation.ui.editcontact

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.presentation.ui.editcontact.component.EditContactContent
import com.example.myapplication.presentation.ui.editcontact.component.EditContactForm
import com.example.myapplication.presentation.ui.editcontact.component.EditContactTopBar
import org.koin.androidx.compose.koinViewModel

/**
 * Screen that allows users to edit an existing contact
 *
 * @param contactId The ID of the contact to edit
 * @param onBackClick Callback when the back button is clicked
 * @param onContactUpdated Callback when a contact is successfully updated
 * @param viewModel The view model for this screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactScreen(
    contactId: String,
    onBackClick: () -> Unit,
    onContactUpdated: () -> Unit,
    viewModel: EditContactViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Load contact when entering the screen
    LaunchedEffect(contactId) {
        viewModel.loadContact(contactId)
    }

    // Handle error and success states
    LaunchedEffect(state) {
        when (state) {
            is EditContactState.Error -> {
                errorMessage = (state as EditContactState.Error).message
                showErrorDialog = true
            }

            is EditContactState.Success -> {
                onContactUpdated()
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            EditContactTopBar(
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            EditContactContent(
                state = state,
                onUpdateContact = { name, phone, email, company ->
                    viewModel.updateContact(contactId, name, phone, email, company) {
                        // This callback is only called on success
                        onContactUpdated()
                    }
                },
                onBackClick = onBackClick
            )
        }
    }

    // Error dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditContactScreenPreview() {
    Scaffold(
        topBar = {
            EditContactTopBar(
                onBackClick = {}
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            EditContactForm(
                name = "John Doe",
                phone = "+1 123 456 7890",
                email = "john.doe@example.com",
                company = "Example Corp",
                onNameChange = {},
                onPhoneChange = {},
                onEmailChange = {},
                onCompanyChange = {},
                onUpdateClick = {}
            )
        }
    }
}
