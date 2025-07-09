package com.example.myapplication.presentation.ui.contactlist

import com.example.myapplication.domain.entity.Contact

sealed class ContactListState {
    object Loading : ContactListState()
    data class Success(val contacts: List<Contact>) : ContactListState()
    data class Error(val message: String) : ContactListState()
}
