package com.example.myapplication.presentation.ui.contactlist.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.contactlist.ContactListState

@Composable
fun ContactListContent(
    state: ContactListState,
    onContactClick: (Contact) -> Unit
) {
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
                    state.message,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }

            is ContactListState.Success -> ContactList(
                contacts = state.contacts,
                onClick = onContactClick
            )
        }
    }
}