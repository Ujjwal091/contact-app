package com.example.myapplication.presentation.ui.contactlist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.presentation.ui.contactlist.ContactListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListTopBar(
    state: ContactListState,
    onAddContactClick: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Phone",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                if (state is ContactListState.Success) {
                    val count = state.contacts.size
                    val text = when (count) {
                        0 -> "No contact found"
                        1 -> "$count contact found"
                        else -> "$count contacts found"
                    }
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        },
        actions = {
//                IconButton(onClick = { /* TODO: implement search */ }) {
//                    Icon(Icons.Default.Search, contentDescription = "Search Contacts")
//                }
            IconButton(onClick = onAddContactClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Contact")
            }
        }
    )
}