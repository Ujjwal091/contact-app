@file:Suppress("UNUSED_PARAMETER")

package com.example.myapplication.presentation.ui.contactlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.contactlist.component.ContactList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel = koinViewModel(),
    onContactClick: (Contact) -> Unit,
    onAddContactClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadContacts()
    }

    Column {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Phone",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    if (state is ContactListState.Success) {
                        val count = (state as ContactListState.Success).contacts.size
                        Text(
                            text = "$count contacts with phone numbers",
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

        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is ContactListState.Loading -> Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is ContactListState.Error -> Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        (state as ContactListState.Error).message,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }

                is ContactListState.Success -> ContactList(
                    contacts = (state as ContactListState.Success).contacts,
                    onClick = onContactClick
                )
            }
        }
    }
}
