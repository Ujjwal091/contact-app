@file:Suppress("UNUSED_PARAMETER")

package com.example.myapplication.presentation.ui.contactlist

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.contactlist.component.ContactListContent
import com.example.myapplication.presentation.ui.contactlist.component.ContactListTopBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel = koinViewModel(),
    onContactClick: (Contact) -> Unit,
    onAddContactClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Generate a unique key each time the composable is recomposed
    // This ensures contacts are reloaded when navigating back to this screen
    val reloadKey = remember { System.currentTimeMillis() }

    LaunchedEffect(reloadKey) {
        viewModel.loadContacts()
    }

    Column {
        ContactListTopBar(state = state, onAddContactClick = onAddContactClick)
        ContactListContent(state = state, onContactClick = onContactClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun ContactListScreenPreview() {
    val sampleContacts = listOf(
        Contact(
            id = "1",
            name = "John Doe",
            phone = "+1 123-456-7890"
        ),
        Contact(
            id = "2",
            name = "Jane Smith",
            phone = "+1 987-654-3210"
        ),
        Contact(
            id = "3",
            name = "Alice Johnson",
            phone = "+1 555-123-4567"
        )
    )

    // Mock UI state directly without ViewModel
    val state: ContactListState = ContactListState.Success(sampleContacts)

    Column {
        ContactListTopBar(state = state, onAddContactClick = {})
        ContactListContent(state = state, onContactClick = {})
    }
}
