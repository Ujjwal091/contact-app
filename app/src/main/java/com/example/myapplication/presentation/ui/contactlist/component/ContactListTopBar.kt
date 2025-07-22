package com.example.myapplication.presentation.ui.contactlist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

/**
 * TopAppBar for the contact list screen.
 * Simplified implementation with direct input handling and minimal state changes.
 *
 * @param contactCount The number of contacts to display in the count text
 * @param isSearchActive Whether the search field is active
 * @param searchQuery The current search query
 * @param onSearchActiveChange Callback when the search active state changes
 * @param onSearchQueryChange Callback when the search query changes
 * @param onAddContactClick Callback when the add contact button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListTopBar(
    contactCount: Int,
    isSearchActive: Boolean,
    searchQuery: String,
    onSearchActiveChange: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onAddContactClick: () -> Unit
) {
    // Create focus requester and manager
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Compute contact count text
    val contactCountText = when (contactCount) {
        0 -> "No contact found"
        1 -> "1 contact found"
        else -> "$contactCount contacts found"
    }

    // Request focus when search becomes active
    DisposableEffect(isSearchActive) {
        if (isSearchActive) {
            try {
                focusRequester.requestFocus()
            } catch (e: Exception) {
                // Ignore focus request exceptions
            }
        }
        onDispose { }
    }

    TopAppBar(
        title = {
            if (isSearchActive) {
                // Use standard TextField for better compatibility and input handling
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text("Search contacts...")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        onSearchActiveChange(false)
                    }),
                    // Use simple colors for better visibility
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                    ),
                    // Add leading icon for better visual cue
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            } else {
                Column {
                    Text(
                        text = "Contacts",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    if (contactCountText.isNotBlank()) {
                        Text(
                            text = contactCountText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        },
        actions = {
            if (isSearchActive) {
                // Close search button with improved handling
                IconButton(
                    onClick = {
                        // First clear focus to dismiss keyboard
                        focusManager.clearFocus()
                        // Then reset search state
                        onSearchQueryChange("")
                        // Finally, deactivate search mode
                        onSearchActiveChange(false)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                // Search button with improved handling
                IconButton(
                    onClick = {
                        // Simply activate search mode
                        // Focus will be requested by DisposableEffect
                        onSearchActiveChange(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Contacts",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                // Add contact button
                IconButton(onClick = onAddContactClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Contact",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListTopBarPreview() {
    return ContactListTopBar(
        contactCount = 5,
        isSearchActive = false,
        searchQuery = "",
        onSearchActiveChange = {},
        onSearchQueryChange = {},
        onAddContactClick = {}
    )
}