package com.example.myapplication.presentation.ui.contactlist

import com.example.myapplication.domain.entity.Contact

sealed class ContactListState {
    object Loading : ContactListState()
    data class Success(
        val contacts: List<Contact>,
        val searchQuery: String = "",
        // Cache the filtered contacts to avoid recomputing on every access
        // This is computed once when the state is created and won't change until a new state is created
        val filteredContacts: List<Contact> = if (searchQuery.isBlank()) {
            contacts
        } else {
            contacts.filter { contact ->
                contact.name.contains(searchQuery, ignoreCase = true) ||
                        contact.phone.contains(searchQuery, ignoreCase = true)
            }
        }
    ) : ContactListState()

    data class Error(val message: String) : ContactListState()
}
