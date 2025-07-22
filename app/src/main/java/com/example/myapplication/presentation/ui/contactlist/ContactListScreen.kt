@file:Suppress("UNUSED_PARAMETER")

package com.example.myapplication.presentation.ui.contactlist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
    // Collect state once and store it in a stable variable
    val state by viewModel.state.collectAsState()

    // Generate a unique key each time the composable is recomposed
    // This ensures contacts are reloaded when navigating back to this screen
    val reloadKey = remember { System.currentTimeMillis() }

    // Use LaunchedEffect without delay to load contacts immediately
    LaunchedEffect(reloadKey) {
        viewModel.loadContacts()
    }

    // Manage search state locally to avoid state management issues
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    // Update the ViewModel when search query changes, but debounce it
    LaunchedEffect(searchQuery) {
        // Small delay to avoid updating on every keystroke
        kotlinx.coroutines.delay(300)
        viewModel.updateSearchQuery(searchQuery)
    }

    Column {
        // Keep the TopAppBar outside of AnimatedContent to prevent animation interference
        ContactListTopBar(
            contactCount = if (state is ContactListState.Success) {
                (state as ContactListState.Success).filteredContacts.size
            } else {
                0
            },
            isSearchActive = isSearchActive,
            searchQuery = searchQuery,
            onSearchActiveChange = { isSearchActive = it },
            onSearchQueryChange = { searchQuery = it },
            onAddContactClick = onAddContactClick
        )

        // Only animate the content below the TopAppBar
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "ContactListStateTransition"
        ) { currentState ->
            ContactListContent(state = currentState, onContactClick = onContactClick)
        }
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
        ContactListTopBar(
            contactCount = (state as ContactListState.Success).contacts.size,
            isSearchActive = false,
            searchQuery = "",
            onSearchActiveChange = {},
            onSearchQueryChange = {},
            onAddContactClick = {}
        )
        ContactListContent(state = state, onContactClick = {})
    }
}
