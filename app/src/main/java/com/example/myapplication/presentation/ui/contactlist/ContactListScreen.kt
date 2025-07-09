package com.example.myapplication.presentation.ui.contactlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.entity.Contact

/**
 * Main screen that displays the list of contacts
 * 
 * @param viewModel The view model for this screen
 * @param onContactClick Callback when a contact is clicked
 * @param onAddContactClick Callback when the add contact button is clicked
 */
@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel = ContactListViewModel(
        getAllContactsUseCase = com.example.myapplication.domain.usecase.GetAllContactsUseCase(
            repository = com.example.myapplication.data.repository.ContactRepositoryImpl(
                dataSource = com.example.myapplication.data.datasource.ContactDataSource(
                    contentResolver = LocalContext.current.contentResolver
                )
            )
        )
    ),
    onContactClick: (Contact) -> Unit,
    onAddContactClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadContacts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddContactClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact"
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                is ContactListState.Loading -> Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }

                is ContactListState.Error -> Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Error: ${(state as ContactListState.Error).message}")
                }

                is ContactListState.Success -> ContactList(
                    contacts = (state as ContactListState.Success).contacts,
                    onClick = onContactClick
                )
            }
        }
    }
}

@Composable
fun ContactList(contacts: List<Contact>, onClick: (Contact) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(contacts) { contact ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(contact) }
                    .padding(16.dp)
            ) {
                Text(text = contact.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = contact.phone)
            }
            Divider()
        }
    }
}
