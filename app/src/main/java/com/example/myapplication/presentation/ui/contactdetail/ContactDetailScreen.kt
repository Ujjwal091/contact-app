package com.example.myapplication.presentation.ui.contactdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.contactdetail.component.ContactDetailContent
import com.example.myapplication.presentation.ui.contactdetail.component.ContactDetailTopBar
import com.example.myapplication.presentation.ui.contactdetail.component.ContactDetails
import com.example.myapplication.presentation.ui.contactdetail.component.DeleteContactDialog
import org.koin.androidx.compose.koinViewModel

/**
 * Screen that displays the details of a contact
 *
 * @param contactId The ID of the contact to display
 * @param onBackClick Callback when the back button is clicked
 * @param onEditClick Callback when the edit button is clicked
 * @param viewModel The view model for this screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contactId: String,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    viewModel: ContactDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Load contact when entering the screen or when returning to it
    // Using a key that changes every time the screen is composed
    val reloadKey = remember { System.currentTimeMillis() }
    LaunchedEffect(reloadKey) {
        viewModel.loadContact(contactId)
    }

    Scaffold(
        topBar = {
            ContactDetailTopBar(
                onBackClick = onBackClick,
                onEditClick = { onEditClick(contactId) },
                onDeleteClick = { showDeleteDialog = true }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ContactDetailContent(state = state)
        }
    }

    if (showDeleteDialog) {
        DeleteContactDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                viewModel.deleteContact(contactId) {
                    showDeleteDialog = false
                    onBackClick()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDetailScreenPreview() {
    val previewContact = Contact(
        id = "1",
        name = "John Doe",
        phone = "+1 123 456 7890",
        email = "john.doe@example.com",
        address = "123 Main St, Anytown, USA",
        company = "Example Corp"
    )


    Scaffold(
        topBar = {
            ContactDetailTopBar(
                onBackClick = {},
                onEditClick = {},
                onDeleteClick = {}
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ContactDetails(contact = previewContact)
        }
    }

}
